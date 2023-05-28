package com.myclub.computerclubmanagement;

import com.myclub.computerclubmanagement.model.club.City;
import com.myclub.computerclubmanagement.model.club.Club;
import com.myclub.computerclubmanagement.model.gamingEquipment.GamingEquipment;
import com.myclub.computerclubmanagement.model.gamingEquipment.Type;
import com.myclub.computerclubmanagement.repository.ClubRepository;
import com.myclub.computerclubmanagement.repository.EquipmentRepository;
import com.myclub.computerclubmanagement.service.ClubService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class ComputerClubManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComputerClubManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ClubRepository repository) {
        return  args -> {
            repository.deleteAll();
        };
    }
}
