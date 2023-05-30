package com.myclub.computerclubmanagement.service;

import com.myclub.computerclubmanagement.dto.ClubRequest;
import com.myclub.computerclubmanagement.dto.ClubResponse;
import com.myclub.computerclubmanagement.dto.GamingEquipmentRequest;
import com.myclub.computerclubmanagement.model.club.City;
import com.myclub.computerclubmanagement.model.club.Club;
import com.myclub.computerclubmanagement.model.gamingEquipment.GamingEquipment;
import com.myclub.computerclubmanagement.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    private final EquipmentService equipmentService;
    public List<ClubResponse> findAll(){
        return clubRepository.findAll().stream().map(this::mapClubToDto).toList();
    }

    public List<ClubResponse> findAllByCity (String city){
        return clubRepository.findAll().stream()
                .filter(club -> club.getCity().equals(City.valueOf(city.toUpperCase())))
                .map(this::mapClubToDto)
                .toList();
    }
    public void save(ClubRequest clubRequest) {
        clubRepository.insert(mapDtoToClub(clubRequest));
    }



    public void addEquipment(String clubName, String city, List<GamingEquipmentRequest> gamingEquipmentRequest) {
        Optional<Club> clubOptional = clubRepository.findAll().stream()
                .filter(el -> el.getName().equals(clubName))
                .filter(el -> el.getCity().name().equalsIgnoreCase(city))
                .findFirst();
        if (clubOptional.isPresent()) {
            Club club = clubOptional.get();
            if (club.getGamingEquipments() == null) {
                club.setGamingEquipments(new ArrayList<>());
            }
            int sizeAfterInsert =  club.getGamingEquipments().size() +
                    gamingEquipmentRequest.size();
            if (sizeAfterInsert > club.getMaxSize()) {

                throw new IllegalStateException("Not enough size for your request. Current size: " + club.getGamingEquipments().size()
                + ". Max size: " + club.getMaxSize() + ". Size after insert: " + sizeAfterInsert+ "."
                );
            }
            List<GamingEquipment> newEqu = new ArrayList<>(club.getGamingEquipments());
            for (int i = 0; i < gamingEquipmentRequest.size(); i++) {
                newEqu.add(equipmentService.save(gamingEquipmentRequest.get(i),club,i));
            }
            club.setGamingEquipments(newEqu);
            clubRepository.save(club);
        }

    }


    private ClubResponse mapClubToDto(Club club) {
        return ClubResponse.builder()
                .name(club.getName())
                .city(club.getCity().name().toUpperCase())
                .maxSize(club.getMaxSize())
                .maxCapacity(club.getMaxSize())
                .totalIncome(club.getTotalIncome() == null ? 0 : club.getTotalIncome())
                .currentCapacity(club.getCurrentCapacity() == null ? 0 : club.getCurrentCapacity())
                .currentSize(club.getGamingEquipments() == null ? 0 : club.getGamingEquipments().size())
                .gamingEquipmentResponseList(
                        club.getGamingEquipments() == null ? null :
                        club.getGamingEquipments().stream()
                                .map(equipmentService::mapEquToDto)
                                .toList()
                )
                .build();
    }



    private Club mapDtoToClub(ClubRequest clubRequest) {
        return Club.builder()
                .name(clubRequest.getName())
                .maxSize(clubRequest.getMaxSize())
                .maxCapacity(clubRequest.getMaxSize())
                .currentCapacity(0)
                .city(City.valueOf(clubRequest.getCity().toUpperCase()))
                .maxCapacity(clubRequest.getMaxSize())
                .currentCapacity(0)
                .totalIncome(0)
                .build();
    }

    @Transactional
    public void delete(String clubName, String city) {
        Optional<Club> optionalClub = clubRepository.findAll().stream()
                .filter(club -> club.getName().equals(clubName))
                .filter(club -> club.getCity().name().equalsIgnoreCase(city))
                .findFirst();

        if (optionalClub.isPresent()) {
            for (GamingEquipment g: optionalClub.get().getGamingEquipments()) {
                equipmentService.delete(g);
            }
            clubRepository.delete(optionalClub.get());
            log.warn("Club: " + clubName + ", in " + city + " has been deleted"  );
        }else {
            throw new IllegalStateException("Club not fount");
        }

    }

    public ClubResponse findByCityAndName(String city, String clubName) {
        Optional<Club> optionalClub = clubRepository.findAll().stream()
                .filter(club -> club.getName().equals(clubName))
                .filter(club -> club.getCity().name().equalsIgnoreCase(city))
                .findFirst();

        if (optionalClub.isPresent()) {
            return mapClubToDto(optionalClub.get());
        }

        throw new IllegalStateException("Club: " + clubName + ", not found in " + city);
    }

    @Transactional
    public void updateClubStat(ClubResponse clubResponse, int duration, int localNumberOfEquipment) {
        Club club = clubRepository.findAll().stream()
                .filter(el -> el.getName().equals(clubResponse.getName()))
                .filter(el -> el.getCity().name().equalsIgnoreCase(clubResponse.getCity()))
                .findFirst().orElseThrow();

        GamingEquipment gamingEquipment = club.getGamingEquipments().stream()
                .filter(el -> el.getLocalNumber() == localNumberOfEquipment)
                .findFirst().orElseThrow();
        int sessionIncome = duration * gamingEquipment.getCostPerHour();
        int currentIncome = club.getTotalIncome() == null ? 0 : club.getTotalIncome();
        equipmentService.updateStatus(gamingEquipment);
        club.setTotalIncome(currentIncome + sessionIncome);
        System.out.println(club);
        clubRepository.save(club);

    }
}
