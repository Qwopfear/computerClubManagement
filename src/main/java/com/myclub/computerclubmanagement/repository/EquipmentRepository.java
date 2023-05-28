package com.myclub.computerclubmanagement.repository;

import com.myclub.computerclubmanagement.model.gamingEquipment.GamingEquipment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EquipmentRepository
    extends MongoRepository<GamingEquipment, String> {
}
