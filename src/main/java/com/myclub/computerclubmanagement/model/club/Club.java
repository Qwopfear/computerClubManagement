package com.myclub.computerclubmanagement.model.club;

import com.myclub.computerclubmanagement.model.gamingEquipment.GamingEquipment;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document
@ToString
public class Club {

    @Id
    private String id;
    private String name;
    private City city;
    private Integer maxSize;

    private Integer maxCapacity;
    private Integer currentCapacity;

    private Integer totalIncome;
    @DBRef
    private List<GamingEquipment> gamingEquipments;


}
