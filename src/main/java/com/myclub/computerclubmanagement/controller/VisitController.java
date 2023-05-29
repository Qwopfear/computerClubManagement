package com.myclub.computerclubmanagement.controller;

import com.myclub.computerclubmanagement.dto.VisitResponse;
import com.myclub.computerclubmanagement.model.gamingEquipment.Type;
import com.myclub.computerclubmanagement.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/visit")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createVisit (
            @RequestParam String city,
            @RequestParam String clubName,
            @RequestParam String userName,
            @RequestParam int duration,
            @RequestParam int localNumberOfEquipment

            ) {
        visitService.createVisit(city,clubName,userName,duration,localNumberOfEquipment);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<VisitResponse> getAllVisits (

    ) {
       return visitService.findAll();
    }

}
