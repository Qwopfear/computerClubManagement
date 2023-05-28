package com.myclub.computerclubmanagement.dto;

import com.myclub.computerclubmanagement.model.gamingEquipment.Type;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GamingEquipmentResponse {
        private Type type;
        private int localNumber;
        private int costPerHouse;
        private boolean isAvailable;
}
