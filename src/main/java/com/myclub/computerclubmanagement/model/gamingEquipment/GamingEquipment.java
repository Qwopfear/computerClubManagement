package com.myclub.computerclubmanagement.model.gamingEquipment;

import com.myclub.computerclubmanagement.model.club.Club;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@ToString(exclude = "club")
@Document
public class GamingEquipment {

    @Id
    private String id;
    private Type type;
    private int localNumber;
    private int costPerHour;
    private boolean isAvailable;
    @DBRef(lazy = true)
    private Club club;
}
