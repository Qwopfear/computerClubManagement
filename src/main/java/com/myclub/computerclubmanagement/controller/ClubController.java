package com.myclub.computerclubmanagement.controller;

import com.myclub.computerclubmanagement.dto.ClubResponse;
import com.myclub.computerclubmanagement.dto.ClubRequest;
import com.myclub.computerclubmanagement.dto.GamingEquipmentRequest;
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
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ClubResponse> getAllClubs(){
        return clubService.findAll();
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<ClubResponse> getClubsByCity(
            @RequestParam String city
    ){
        return clubService.findAllByCity(city);
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

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteClub(
            @RequestParam String clubName,
            @RequestParam String city
    ) {
        clubService.delete(clubName,city);
    }
}
