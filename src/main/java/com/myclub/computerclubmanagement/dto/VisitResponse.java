package com.myclub.computerclubmanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VisitResponse {
    private LocalDateTime begin;
    private int duration;
    private String clubName;
    private String city;
    private String username;
    private int equLocalNumber;
    private boolean isActive;
    private int cost;
}
