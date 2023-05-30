package com.myclub.computerclubmanagement.service;

import com.myclub.computerclubmanagement.dto.GamingEquipmentRequest;
import com.myclub.computerclubmanagement.dto.GamingEquipmentResponse;
import com.myclub.computerclubmanagement.model.club.Club;
import com.myclub.computerclubmanagement.model.gamingEquipment.GamingEquipment;
import com.myclub.computerclubmanagement.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
                .costPerHour(el.getCostPerHour())
                .localNumber(el.getLocalNumber())
                .isAvailable(el.isAvailable())
                .build();
    }

    public GamingEquipment mapDtoToEqu(GamingEquipmentRequest gamingEquipmentResponse) {
        return GamingEquipment.builder()
                .type(gamingEquipmentResponse.getType())
                .costPerHour(gamingEquipmentResponse.getCostPerHour())
                .isAvailable(true)
                .build();
    }

    public GamingEquipment mapDtoToEqu(GamingEquipmentRequest gamingEquipmentRequest,Club club, int index) {
        return GamingEquipment.builder()
                .type(gamingEquipmentRequest.getType())
                .costPerHour(gamingEquipmentRequest.getCostPerHour())
                .isAvailable(true)
                .localNumber(club.getGamingEquipments().size() + index)
                .club(club)
                .build();
    }


    public void delete(GamingEquipment g) {
        equipmentRepository.delete(g);
        log.warn("Gaming eq nr " + g.getLocalNumber() + " in " + g.getClub().getName() + " has been deleted");
    }

    public void updateStatus(GamingEquipment gamingEquipment) {
        gamingEquipment.setAvailable(!gamingEquipment.isAvailable());
        equipmentRepository.save(gamingEquipment);
    }
}
