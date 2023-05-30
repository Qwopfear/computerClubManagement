package com.myclub.computerclubmanagement.model.visit;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document
public class Visit {

    private String id;
    private LocalDateTime begin;
    private int duration;
    private String clubName;
    private String city;
    private String username;
    private int equLocalNumber;
    private boolean isActive;
    private int cost;
}
