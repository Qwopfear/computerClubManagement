package com.myclub.computerclubmanagement.dto;

import com.myclub.computerclubmanagement.model.club.City;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClubResponse {
    private String name;
    private Integer maxSize;
    private String city;
    private Integer currentSize;
    private Integer maxCapacity;
    private Integer currentCapacity;
    private List<GamingEquipmentResponse> gamingEquipmentResponseList;

}
