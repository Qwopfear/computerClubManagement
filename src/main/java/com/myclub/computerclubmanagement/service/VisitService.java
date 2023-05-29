package com.myclub.computerclubmanagement.service;

import com.myclub.computerclubmanagement.dto.*;
import com.myclub.computerclubmanagement.model.visit.Visit;
import com.myclub.computerclubmanagement.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitService {

    private final ClubService clubService;
    private final CustomerService customerService;

    private final VisitRepository visitRepository;

    @Transactional
    public void createVisit(String city, String clubName, String userName, int duration, int localNumberOfEquipment) {
        if (duration < 1 || duration > 12) {
            throw new IllegalStateException("Min session duration is 1 hour max 12 hours");
        }
        ClubResponse clubResponse = clubService.findByCityAndName(city,clubName);
        CustomerResponse customerResponse = customerService.findByUsername(userName);
        GamingEquipmentResponse ger = clubResponse.getGamingEquipmentResponseList().stream()
                .filter(el -> el.getLocalNumber() == localNumberOfEquipment)
                .findFirst().orElseThrow();
        if (!ger.isAvailable()) throw new IllegalStateException("Machine nr " + localNumberOfEquipment + ", is not available right now");

        Visit visit = Visit.builder()
                .clubName(clubName)
                .city(city)
                .username(userName)
                .begin(LocalDateTime.now())
                .duration(duration)
                .equLocalNumber(localNumberOfEquipment)
                .cost(ger.getCostPerHour() * duration)
                .build();

        visitRepository.insert(visit);
        log.info("Customer " + userName + " visited " + clubName + " in " + city + ", and spent " + duration  + (duration > 1 ? " hours" : " hour!" ));
        customerService.updateCustomerStat(customerResponse, duration, ger.getCostPerHour());
        clubService.updateClubStat(clubResponse, duration,localNumberOfEquipment);




    }

    public List<VisitResponse> findAll() {
        return visitRepository.findAll().stream().map(this::mapVisitToDto).toList();
    }

    private VisitResponse mapVisitToDto(Visit visit) {
        return VisitResponse.builder()
                .begin(visit.getBegin())
                .duration(visit.getDuration())
                .equLocalNumber(visit.getEquLocalNumber())
                .username(visit.getUsername())
                .city(visit.getCity())
                .clubName(visit.getClubName())
                .cost(visit.getCost())
                .build();

    }
}
