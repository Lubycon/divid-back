package com.lubycon.ourney.domains.trip.service;

import com.lubycon.ourney.domains.trip.dto.SaveTripRequest;
import com.lubycon.ourney.domains.trip.entity.Trip;
import com.lubycon.ourney.domains.trip.entity.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TripService {
    private final TripRepository tripRepository;


    public void save(SaveTripRequest saveTripRequest){
        // user.id 받아서 넣기
        Trip trip = Trip.builder()
                .tripName(saveTripRequest.getTripNm())
                .startDate(saveTripRequest.getStartDate())
                .endDate(saveTripRequest.getEndDate())
                //.url("http://localhost:8080/trips/")
                .build();

        tripRepository.save(trip);
    }

}
