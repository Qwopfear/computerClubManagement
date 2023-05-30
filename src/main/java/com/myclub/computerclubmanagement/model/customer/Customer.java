package com.myclub.computerclubmanagement.model.customer;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class Customer {

    private String id;
    private String username;
    private Status status;
    private int totalSpendingMoney;
    private int totalSpendingTime;
    private boolean inClub;

}
