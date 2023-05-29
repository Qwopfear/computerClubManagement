package com.myclub.computerclubmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomerRequest {
    private String username;
    private int totalSpendingTime;
    private int totalSpendingMoney;
}
