package com.myclub.computerclubmanagement.model.club;

import com.myclub.computerclubmanagement.model.gamingEquipment.GamingEquipment;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Document
public class Club {

    @Id
    private String id;
    private String name;
    private String city;
    private int maxSize;
    @DBRef
    private List<GamingEquipment> gamingEquipments = new ArrayList<>();
}
