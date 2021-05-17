package com.lubycon.ourney.domains.trip.service;

import com.lubycon.ourney.common.exception.ApiException;
import com.lubycon.ourney.common.exception.ExceptionEnum;
import com.lubycon.ourney.domains.trip.entity.Trip;
import com.lubycon.ourney.domains.trip.entity.TripRepository;
import com.lubycon.ourney.domains.trip.entity.UserTripMap;
import com.lubycon.ourney.domains.trip.entity.UserTripMapRepository;
import com.lubycon.ourney.domains.user.entity.User;
import com.lubycon.ourney.domains.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserTripMapService {
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final UserTripMapRepository userTripMapRepository;

    public void saveMap(long id, UUID tripId){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ApiException(ExceptionEnum.NOT_FOUND_EXCEPTION));

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(()-> new ApiException(ExceptionEnum.NOT_FOUND_EXCEPTION));

        UserTripMap userTripMap = UserTripMap.builder()
                .user(user)
                .trip(trip)
                .build();
        userTripMapRepository.save(userTripMap);
    }

}
