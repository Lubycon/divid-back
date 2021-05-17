package com.lubycon.ourney.domains.trip.controller;

import com.lubycon.ourney.common.Constants;
import com.lubycon.ourney.common.ResponseMessages;
import com.lubycon.ourney.common.config.interceptor.LoginId;
import com.lubycon.ourney.common.exception.ApiException;
import com.lubycon.ourney.common.exception.ExceptionEnum;
import com.lubycon.ourney.common.exception.SimpleSuccessResponse;
import com.lubycon.ourney.domains.trip.dto.CreateTripRequest;
import com.lubycon.ourney.domains.trip.dto.TripListResponse;
import com.lubycon.ourney.domains.trip.dto.TripResponse;
import com.lubycon.ourney.domains.trip.service.TripService;
import com.lubycon.ourney.domains.trip.service.UserTripMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/trips")
@RestController
public class TripController {
    private final TripService tripService;
    private final UserTripMapService userTripMapService;

    @ApiOperation("여행 리스트 조회")
    @GetMapping("/all")
    public ResponseEntity<List<TripListResponse>> getTripList(@LoginId long id) {
        return ResponseEntity.ok(tripService.getTripList(id));
    }

    @ApiOperation("여행 상세 조회")
    @GetMapping("")
    public ResponseEntity getTripList(@LoginId long id, @RequestParam("tripId") UUID tripId, @RequestHeader(Constants.INVITE_CODE) String inviteCode) {
        if (tripService.checkTripAuth(id, tripId, inviteCode)) {
            return ResponseEntity.ok(tripService.getTrip(tripId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ApiOperation("여행 생성")
    @PostMapping("")
    public ResponseEntity<SimpleSuccessResponse> createTrip(
            @LoginId long id,
            @RequestBody CreateTripRequest createTripRequest
    ) {
        UUID tripId = tripService.saveTrip(id, createTripRequest);
        tripService.updateUrl(tripId);
        userTripMapService.saveMap(id, tripId);
        return ResponseEntity.ok(new SimpleSuccessResponse(ResponseMessages.SUCCESS_CREATE_TRIP));
    }
}