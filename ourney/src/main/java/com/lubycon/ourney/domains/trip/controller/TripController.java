package com.lubycon.ourney.domains.trip.controller;

import com.lubycon.ourney.domains.trip.dto.SaveTripRequest;
import com.lubycon.ourney.domains.trip.entity.Trip;
import com.lubycon.ourney.domains.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/trips")
public class TripController {
    private final TripService tripService;

    @PostMapping("/")
    public String saveTrip(SaveTripRequest saveTripRequest){
        tripService.save(saveTripRequest);
        return "Trip Save OK";
    }
}
