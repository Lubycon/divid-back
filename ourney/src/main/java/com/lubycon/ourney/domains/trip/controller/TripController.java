package com.lubycon.ourney.domains.trip.controller;

import com.lubycon.ourney.common.dto.SimpleSuccessResponse;
import com.lubycon.ourney.domains.trip.dto.SaveTripRequest;
import com.lubycon.ourney.domains.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/trips")
public class TripController {
    private final TripService tripService;

    @PostMapping("")
    public ResponseEntity<?> saveTrip(HttpServletResponse res,SaveTripRequest saveTripRequest){
        log.info("SAVE TRIP");
        Long id = Long.parseLong(res.getHeader("id"));
        saveTripRequest.setOwnerId(id);
        log.info("OWNER : "+saveTripRequest.getOwnerId());
        tripService.save(saveTripRequest);
        tripService.makeUrl(id);
        SimpleSuccessResponse response = new SimpleSuccessResponse("여행 저장 성공");
        return ResponseEntity.ok(response);
    }
}
