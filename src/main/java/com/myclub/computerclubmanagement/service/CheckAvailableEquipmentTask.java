package com.myclub.computerclubmanagement.service;

import com.myclub.computerclubmanagement.dto.VisitResponse;
import com.myclub.computerclubmanagement.model.club.Club;
import com.myclub.computerclubmanagement.model.customer.Customer;
import com.myclub.computerclubmanagement.model.gamingEquipment.GamingEquipment;
import com.myclub.computerclubmanagement.model.visit.Visit;
import com.myclub.computerclubmanagement.repository.ClubRepository;
import com.myclub.computerclubmanagement.repository.EquipmentRepository;
import com.myclub.computerclubmanagement.repository.VisitRepository;
import com.myclub.computerclubmanagement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CheckAvailableEquipmentTask {

    private final EquipmentService equipmentService;
    private final ClubRepository clubService;
    private final CustomerService customerService;
    private final VisitRepository visitService;
    static final int time = 5000;
    @Scheduled(fixedRate = time)
    public void task() {
        System.out.println(clubService.findAll());
        System.out.println(customerService.findAll());
        System.out.println(visitService.findAll());
        List<Visit> listVisit = last12HoursVisits();
        List<Club> clubList = last12HoursClubs(listVisit);
        updateStatus(listVisit, clubList);
    }

    private void updateStatus(List<Visit> listVisit, List<Club> clubList) {
        for (Club club : clubList) {
            List<Visit> currentClubVisits = getCurrentClubVisits(listVisit, club.getName());
            for (Visit visit: currentClubVisits) {
                // Check each visit in club from last 12 hours if visit + duration is before now, that means visit is ended
                // So we have to enable equipment and customer for next visits
                if (visit.getBegin().plusSeconds(visit.getDuration()).isBefore(LocalDateTime.now())) {
                    changeCustomerStatus(visit);
                    changeEquStatus(club, visit.getEquLocalNumber());
                    changeVisitStatus(visit);
                }
            }
        }
    }

    private void changeVisitStatus(Visit visit) {
        visit.setActive(false);
        visitService.save(visit);
    }

    private void changeEquStatus(Club club, int localNumber) {
        GamingEquipment gamingEquipment = club.getGamingEquipments().stream()
                .filter(el -> el.getLocalNumber() == localNumber)
                .findFirst().orElseThrow();
        
        equipmentService.updateStatus(gamingEquipment);
    }

    private void changeCustomerStatus(Visit visit) {
        Customer customer = customerService.getCustomerByUsername(visit.getUsername());
        customer.setInClub(false);
        customerService.updateCustomer(customer);
    }

    private List<Visit> getCurrentClubVisits(List<Visit> listVisit, String clubName) {
        return listVisit.stream()
                .filter(el -> el.getClubName().equals(clubName))
                .toList();
    }

    private List<Club> last12HoursClubs(List<Visit> listVisit) {
        return clubService.findAll().stream()
                .filter(club -> listVisit.stream().map(Visit::getClubName).toList().contains(club.getName()))
                .toList();
    }

    private List<Visit> last12HoursVisits() {
        return visitService.findAll().stream()
                .filter(el -> el.getBegin().isAfter(LocalDateTime.now().minusHours(12)))
                .filter(Visit::isActive)
                .toList();
    }


}
