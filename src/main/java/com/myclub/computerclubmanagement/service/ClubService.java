package com.myclub.computerclubmanagement.service;

import com.myclub.computerclubmanagement.dto.ClubRequest;
import com.myclub.computerclubmanagement.dto.ClubResponse;
import com.myclub.computerclubmanagement.dto.GamingEquipmentRequest;
import com.myclub.computerclubmanagement.model.club.City;
import com.myclub.computerclubmanagement.model.club.Club;
import com.myclub.computerclubmanagement.model.gamingEquipment.GamingEquipment;
import com.myclub.computerclubmanagement.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    private final EquipmentService equipmentService;
    public List<ClubResponse> findAll(){
        return clubRepository.findAll().stream().map(this::mapClubToDto).toList();
    }

    public void save(ClubRequest clubRequest) {
        clubRepository.insert(mapDtoToClub(clubRequest));
    }



    public void addEquipment(String clubName, String city, List<GamingEquipmentRequest> gamingEquipmentRequest) {
        Optional<Club> clubOptional = clubRepository.findAll().stream()
                .filter(el -> el.getName().equals(clubName))
                .filter(el -> el.getCity().equals(city))
                .findFirst();
        if (clubOptional.isPresent()) {
            Club club = clubOptional.get();
            if (club.getGamingEquipments() == null) {
                club.setGamingEquipments(new ArrayList<>());
            }
            if (club.getGamingEquipments().size() +
                    gamingEquipmentRequest.size() > club.getMaxSize()) {
                throw new IllegalStateException("");
            }
            List<GamingEquipment> newEqu = new ArrayList<>(club.getGamingEquipments());
            newEqu.addAll(gamingEquipmentRequest.stream()
                    .map(el -> equipmentService.save(el,club))
                    .toList());
            club.setGamingEquipments(newEqu);
            clubRepository.save(club);
        }

    }


    private ClubResponse mapClubToDto(Club club) {
        return ClubResponse.builder()
                .name(club.getName())
                .city(City.valueOf(club.getCity()))
                .maxSize(club.getMaxSize())
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
                .city(String.valueOf(clubRequest.getCity()))
                .build();
    }
}
