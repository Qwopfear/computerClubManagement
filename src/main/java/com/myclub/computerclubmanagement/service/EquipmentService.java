package com.myclub.computerclubmanagement.service;

import com.myclub.computerclubmanagement.dto.GamingEquipmentRequest;
import com.myclub.computerclubmanagement.dto.GamingEquipmentResponse;
import com.myclub.computerclubmanagement.model.club.Club;
import com.myclub.computerclubmanagement.model.gamingEquipment.GamingEquipment;
import com.myclub.computerclubmanagement.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public GamingEquipment save(GamingEquipmentRequest gamingEquipment,Club club , int index) {
       return equipmentRepository.save(mapDtoToEqu(gamingEquipment,club, index));
    }

    public GamingEquipmentResponse mapEquToDto(GamingEquipment el) {
        return  GamingEquipmentResponse.builder()
                .type(el.getType())
                .costPerHouse(el.getCostPerHouse())
                .localNumber(el.getLocalNumber())
                .isAvailable(el.isAvailable())
                .build();
    }

    public GamingEquipment mapDtoToEqu(GamingEquipmentRequest gamingEquipmentResponse) {
        return GamingEquipment.builder()
                .type(gamingEquipmentResponse.getType())
                .costPerHouse(gamingEquipmentResponse.getCostPerHouse())
                .isAvailable(gamingEquipmentResponse.isAvailable())
                .build();
    }

    public GamingEquipment mapDtoToEqu(GamingEquipmentRequest gamingEquipmentRequest,Club club, int index) {
        return GamingEquipment.builder()
                .type(gamingEquipmentRequest.getType())
                .costPerHouse(gamingEquipmentRequest.getCostPerHouse())
                .isAvailable(gamingEquipmentRequest.isAvailable())
                .localNumber(club.getGamingEquipments().size() + index)
                .club(club)
                .build();
    }
}
