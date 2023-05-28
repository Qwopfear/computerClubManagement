package com.myclub.computerclubmanagement.model.gamingEquipment;

import com.mongodb.lang.NonNull;
import com.myclub.computerclubmanagement.model.club.Club;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Document
public class GamingEquipment {

    @Id
    private String id;
    private Type type;
    private int localNumber;
    private int costPerHouse;
    private boolean isAvailable;
    @DBRef(lazy = true)
    private Club club;
}
