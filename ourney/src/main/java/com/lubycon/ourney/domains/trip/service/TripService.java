package com.lubycon.ourney.domains.trip.service;

import com.lubycon.ourney.domains.trip.dto.SaveTripRequest;
import com.lubycon.ourney.domains.trip.entity.Mapping;
import com.lubycon.ourney.domains.trip.entity.MappingRepository;
import com.lubycon.ourney.domains.trip.entity.Trip;
import com.lubycon.ourney.domains.trip.entity.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TripService {
    private final TripRepository tripRepository;
    private final MappingRepository mappingRepository;

    public void save(SaveTripRequest saveTripRequest){
        Trip trip = Trip.builder()
                .tripName(saveTripRequest.getTripNm())
                .ownerId(saveTripRequest.getOwnerId())
                .startDate(saveTripRequest.getStartDate())
                .endDate(saveTripRequest.getEndDate())
                .build();
        tripRepository.save(trip);
        // Mapping INSERT
        Long tripId = tripRepository.findIdbyTripName(saveTripRequest.getTripNm(), saveTripRequest.getOwnerId());

        Mapping mapping = Mapping.builder()
                .tripId(tripId)
                .userId(saveTripRequest.getOwnerId())
                .approve(true)
                .build();
        mappingRepository.save(mapping);
        log.info("TRIP ID "+tripId);
        makeUrl(tripId);
    }
    @Transactional
    public void makeUrl(Long tripId){
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(()-> new IllegalArgumentException("해당 여행 없습니다. "+tripId));
        String url = "http://localhost:8080/trips/"+tripId;
        log.info("URL : "+url);
        trip.update(url);
    }
}
