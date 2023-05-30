package com.myclub.computerclubmanagement;

import com.myclub.computerclubmanagement.repository.ClubRepository;
import com.myclub.computerclubmanagement.repository.CustomerRepository;
import com.myclub.computerclubmanagement.repository.EquipmentRepository;
import com.myclub.computerclubmanagement.repository.VisitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = "com.myclub.computerclubmanagement")
@EnableScheduling
public class ComputerClubManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComputerClubManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner run(CustomerRepository customerRepository, ClubRepository clubRepository, VisitRepository visitRepository) {
        return args -> {
            clubRepository.deleteAll();
            customerRepository.deleteAll();
            visitRepository.deleteAll();
        };
    }

}
