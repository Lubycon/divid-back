package com.lubycon.ourney.domains.trip.controller;

import com.lubycon.ourney.common.Constants;
import com.lubycon.ourney.common.ResponseMessages;
import com.lubycon.ourney.common.config.interceptor.LoginId;
import com.lubycon.ourney.common.error.SimpleSuccessResponse;
import com.lubycon.ourney.domains.trip.dto.CreateTripRequest;
import com.lubycon.ourney.domains.trip.dto.TripListResponse;
import com.lubycon.ourney.domains.trip.dto.UpdateTripRequest;
import com.lubycon.ourney.domains.trip.exception.TripAccessDeniedException;
import com.lubycon.ourney.domains.trip.service.TripService;
import com.lubycon.ourney.domains.trip.service.UserTripMapService;
import com.lubycon.ourney.domains.user.dto.UserInfoResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/trips")
@RestController
public class TripController {
    private final TripService tripService;
    private final UserTripMapService userTripMapService;

    @ApiOperation("여행 리스트 조회")
    @GetMapping("/all")
    public ResponseEntity<List<TripListResponse>> getTripDetail(@LoginId long id) {
        return ResponseEntity.ok(tripService.getTripList(id));
    }

    @ApiOperation("여행 상세 조회")
    @GetMapping("")
    public ResponseEntity getTripDetail(@LoginId long id, @RequestParam("tripId") UUID tripId, @RequestHeader(Constants.INVITE_CODE) String inviteCode) throws TripAccessDeniedException {
        tripService.checkTripAuth(id, tripId, inviteCode);
        return ResponseEntity.ok(tripService.getTrip(id, tripId));
    }

    @ApiOperation("여행 생성")
    @PostMapping("")
    public ResponseEntity<SimpleSuccessResponse> createTrip(
            @LoginId long id,
            @Valid @RequestBody CreateTripRequest createTripRequest
    ) {
        UUID tripId = tripService.saveTrip(id, createTripRequest);
        userTripMapService.saveMap(id, tripId);
        return ResponseEntity.ok(new SimpleSuccessResponse(ResponseMessages.SUCCESS_CREATE_TRIP));
    }

    @ApiOperation("여행 정보 수정")
    @PutMapping("")
    public ResponseEntity<SimpleSuccessResponse> updateTrip(
            @RequestParam("tripId") UUID tripId,
            @Valid @RequestBody UpdateTripRequest updateTripRequest
    ) {
        tripService.updateTripInfo(tripId, updateTripRequest);
        return ResponseEntity.ok(new SimpleSuccessResponse(ResponseMessages.SUCCESS_UPDATE_TRIP));
    }

    @ApiOperation("여행 나가기")
    @PostMapping("/exit")
    public ResponseEntity<SimpleSuccessResponse> exitTrip(
            @LoginId long id,
            @RequestParam("tripId") UUID tripId) {
        tripService.exitTrip(id, tripId);
        return ResponseEntity.ok().body(new SimpleSuccessResponse(ResponseMessages.SUCCESS_EXIT_TRIP));
    }

    @ApiOperation("여행 삭제")
    @DeleteMapping("")
    public ResponseEntity<SimpleSuccessResponse> deleteTrip(
            @RequestParam("tripId") UUID tripId) {
        tripService.deleteTrip(tripId);
        return ResponseEntity.ok().body(new SimpleSuccessResponse(ResponseMessages.SUCCESS_DELETE_TRIP));
    }

    @ApiOperation("여행 유저 조회")
    @GetMapping("/members")
    public ResponseEntity<List<UserInfoResponse>> getMember(@LoginId long id, @RequestParam UUID tripId) {
        return ResponseEntity.ok().body(tripService.getMember(id, tripId));
    }

}