package com.myclub.computerclubmanagement.controller;

import com.myclub.computerclubmanagement.dto.ClubResponse;
import com.myclub.computerclubmanagement.dto.ClubRequest;
import com.myclub.computerclubmanagement.dto.GamingEquipmentRequest;
import com.myclub.computerclubmanagement.dto.GamingEquipmentResponse;
import com.myclub.computerclubmanagement.model.club.City;
import com.myclub.computerclubmanagement.model.gamingEquipment.GamingEquipment;
import com.myclub.computerclubmanagement.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<ClubResponse> getAllClubs(){
        return clubService.findAll();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createClub(
            @RequestBody ClubRequest clubRequest
    ) {
        clubService.save(clubRequest);
    }

    @PatchMapping("/equ")
    @ResponseStatus(HttpStatus.OK)
    public void addEquipment(
            @RequestParam String clubName,
            @RequestParam String city,
            @RequestBody List<GamingEquipmentRequest> gamingEquipmentRequest
            ) {
        clubService.addEquipment(clubName,city,gamingEquipmentRequest);
    }
}
