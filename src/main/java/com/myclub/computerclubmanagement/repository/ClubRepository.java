package com.myclub.computerclubmanagement.repository;

import com.myclub.computerclubmanagement.model.club.Club;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository
    extends MongoRepository<Club,String> {
}
