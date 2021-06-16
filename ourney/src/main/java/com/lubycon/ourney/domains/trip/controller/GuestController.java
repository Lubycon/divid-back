package com.lubycon.ourney.domains.trip.controller;

import com.lubycon.ourney.domains.trip.dto.TripInfoResponse;
import com.lubycon.ourney.domains.trip.exception.TripNotFoundException;
import com.lubycon.ourney.domains.trip.service.TripService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/guest")
@RestController
public class GuestController {
    private final TripService tripService;

    @ApiOperation("게스트 여행 조회")
    @GetMapping("")
    public ResponseEntity<TripInfoResponse> getTripInfo(@RequestParam("tripId") UUID tripId) throws TripNotFoundException{
        return ResponseEntity.ok(tripService.getTripInfo(tripId));
    }

}
