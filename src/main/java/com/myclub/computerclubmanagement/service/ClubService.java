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
                .build();
    }

    public void delete(String clubName, String city) {
        Optional<Club> optionalClub = clubRepository.findAll().stream()
                .filter(club -> club.getName().equals(clubName))
                .filter(club -> club.getCity().name().equalsIgnoreCase(city))
                .findFirst();

        if (optionalClub.isPresent()) {
            clubRepository.delete(optionalClub.get());
            log.warn("Club: " + clubName + ", in " + city + " has been deleted"  );
        }else {
            throw new IllegalStateException("Club not fount");
        }

    }
}
