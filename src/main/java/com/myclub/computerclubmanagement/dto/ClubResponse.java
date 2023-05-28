package com.myclub.computerclubmanagement.dto;

import com.myclub.computerclubmanagement.model.club.City;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClubResponse {
    private String name;
    private int maxSize;
    private City city;
    private List<GamingEquipmentResponse> gamingEquipmentResponseList;
}
