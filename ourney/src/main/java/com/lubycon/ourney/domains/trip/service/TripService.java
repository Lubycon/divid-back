package com.lubycon.ourney.domains.trip.service;

import com.lubycon.ourney.common.Constants;
import com.lubycon.ourney.common.ResponseMessages;
import com.lubycon.ourney.common.exception.ApiException;
import com.lubycon.ourney.common.exception.ExceptionEnum;
import com.lubycon.ourney.domains.trip.dto.CreateTripRequest;
import com.lubycon.ourney.domains.trip.dto.TripListResponse;
import com.lubycon.ourney.domains.trip.dto.TripResponse;
import com.lubycon.ourney.domains.trip.dto.UserTripMapRequest;
import com.lubycon.ourney.domains.trip.entity.Trip;
import com.lubycon.ourney.domains.trip.entity.TripRepository;
import com.lubycon.ourney.domains.trip.entity.UserTripMap;
import com.lubycon.ourney.domains.trip.entity.UserTripMapRepository;
import com.lubycon.ourney.domains.user.dto.UserInfoDto;
import com.lubycon.ourney.domains.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TripService {
    private final TripRepository tripRepository;
    private final UserTripMapRepository userTripMapRepository;
    private final UserTripMapService userTripMapService;
    public UUID saveTrip(long userId, CreateTripRequest createTripRequest) {
        Trip trip = Trip.builder()
                .tripName(createTripRequest.getTripName())
                .startDate(createTripRequest.getStartDate())
                .endDate(createTripRequest.getEndDate())
                .inviteCode(createTripRequest.getEnterCode())
                .isEnded(checkTripStatus(createTripRequest.getStartDate(), createTripRequest.getEndDate()))
                .ownerId(userId)
                .build();
        tripRepository.save(trip);

        return tripRepository.findIdbyTripName(createTripRequest.getTripName(), userId);
    }

    public boolean checkTripStatus(LocalDate startDate, LocalDate endDate){
        LocalDate localDate = LocalDate.now();
        if(localDate.isAfter(startDate) && localDate.isBefore(endDate)) {
            return false;
        }else
            return true;
    }
    @Transactional
    public void updateUrl(UUID tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(()-> new ApiException(ExceptionEnum.NOT_EXIST_EXCEPTION));
        trip.updateUrl(Constants.LOCAL_TRIP_URL+tripId.toString().replace("-",""));
    }

    public TripResponse getTrip(UUID tripId){
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(()-> new ApiException(ExceptionEnum.NOT_EXIST_EXCEPTION));
        List<UserInfoDto> userInfoDtoList = userTripMapRepository.findAllByTripId(tripId);
        return new TripResponse(trip.getTripId(), trip.getTripName(), trip.getStartDate(), trip.getEndDate(), userInfoDtoList);
    }

    public List<TripListResponse> getTripList(long id) {
        List<TripListResponse> responses = tripRepository.findAllByUserId(id);
        for(TripListResponse response : responses){
            response.updateMemberCnt(tripRepository.findByTripId(response.getTripId()));
        }
        return responses;
    }

    public boolean checkTripAuth(long id, UUID tripId, String inviteCode){
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(()-> new ApiException(ExceptionEnum.NOT_EXIST_EXCEPTION));
        Optional<UserTripMap> userTripMap = userTripMapRepository.findUserTripMapByTripAndUser(id, trip.getTripId());
        if(trip.getInviteCode().equals(inviteCode) && !userTripMap.isEmpty()){ return true;}
        else if(trip.getInviteCode().equals(inviteCode) && userTripMap.isEmpty()){
            enrollTrip(id, tripId);
            return true;
        }
        else return false;
    }

    @Transactional
    public void enrollTrip(long id, UUID tripId) {
        userTripMapService.saveMap(id, tripId);
    }
}
