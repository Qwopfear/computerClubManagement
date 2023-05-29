package com.myclub.computerclubmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClubRequest {
    private String name;
    private int maxSize;
    private String city;
}
