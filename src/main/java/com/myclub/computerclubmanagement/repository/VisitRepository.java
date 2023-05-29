package com.myclub.computerclubmanagement.repository;

import com.myclub.computerclubmanagement.model.visit.Visit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends MongoRepository<Visit, String> {
}
