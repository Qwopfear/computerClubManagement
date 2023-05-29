package com.myclub.computerclubmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {
    private String username;
    private String status;
    private int totalTimeSpending;
    private int totalMoneySpending;
}
