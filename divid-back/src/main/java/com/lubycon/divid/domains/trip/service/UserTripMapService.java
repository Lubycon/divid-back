package com.lubycon.divid.domains.trip.service;

import com.lubycon.divid.domains.trip.entity.Trip;
import com.lubycon.divid.domains.trip.entity.TripRepository;
import com.lubycon.divid.domains.trip.entity.UserTripMap;
import com.lubycon.divid.domains.trip.entity.UserTripMapRepository;
import com.lubycon.divid.domains.trip.exception.TripNotFoundException;
import com.lubycon.divid.domains.user.entity.User;
import com.lubycon.divid.domains.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserTripMapService {
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final UserTripMapRepository userTripMapRepository;

    public void saveMap(long id, UUID tripId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException(tripId + "값에 해당하는 여행이 없습니다."));

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException(tripId + "값에 해당하는 여행이 없습니다."));

        UserTripMap userTripMap = UserTripMap.builder()
                .user(user)
                .trip(trip)
                .build();
        userTripMapRepository.save(userTripMap);
    }

}
