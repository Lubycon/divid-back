package com.lubycon.ourney.domains.trip.service;

import com.lubycon.ourney.domains.expense.dto.AmountResponse;
import com.lubycon.ourney.domains.expense.dto.ExpensePersonalList;
import com.lubycon.ourney.domains.expense.entity.ExpenseRepository;
import com.lubycon.ourney.domains.trip.dto.*;
import com.lubycon.ourney.domains.trip.entity.Trip;
import com.lubycon.ourney.domains.trip.entity.TripRepository;
import com.lubycon.ourney.domains.trip.entity.UserTripMap;
import com.lubycon.ourney.domains.trip.entity.UserTripMapRepository;
import com.lubycon.ourney.domains.trip.exception.TripAccessDeniedException;
import com.lubycon.ourney.domains.trip.exception.TripNotFoundException;
import com.lubycon.ourney.domains.user.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
@RequiredArgsConstructor
@Service
public class TripService {
    private final TripRepository tripRepository;
    private final UserTripMapRepository userTripMapRepository;
    private final UserTripMapService userTripMapService;
    private final ExpenseRepository expenseRepository;

    public List<TripListResponse> getTripList(long id) {
        List<TripListResponse> tripListResponseList = tripRepository.findAllByUserId(id);
        for (TripListResponse tripListResponse : tripListResponseList) {
            List<UserInfoResponse> userInfoResponseList = userTripMapRepository.findAllByTripId(tripListResponse.getTripId());
            tripListResponse.updateTripInfo(tripRepository.findByTripId(tripListResponse.getTripId()), userInfoResponseList);
            modifyUserInfoResponseList(id, userInfoResponseList);
        }
        return tripListResponseList;
    }

    public TripResponse getTrip(long id, UUID tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException(tripId + "값에 해당하는 여행이 없습니다."));
        List<UserInfoResponse> userInfoResponseList = userTripMapRepository.findAllByTripId(tripId);
        modifyUserInfoResponseList(id, userInfoResponseList);
        return new TripResponse(trip.getTripId(), trip.getTripName(), trip.getInviteCode(), trip.getStartDate(), trip.getEndDate(), getAmount(id, tripId), userInfoResponseList);
    }
    public AmountResponse getAmount(long id, UUID tripId){
        //조회하는 사람 기준으로 ( 갚을 돈: 남이 낸 것 중 - 본인의 몫 사용 내역 / 받을 돈 : 내가 내고 - 남이 먹은 것)
        List<ExpensePersonalList> responses = expenseRepository.findAllByTripId(tripId);
        AmountResponse response = AmountResponse.builder()
                .giveAmount(responses.stream()
                        .filter(l->l.getPayerId()!=id)
                        .filter(l->l.getUserId()==id)
                        .mapToLong(l->l.getPrice()).sum())
                .takeAmount(responses.stream()
                        .filter(l->l.getPayerId()==id)
                        .filter(l->l.getUserId()!=id)
                        .mapToLong(l->l.getPrice()).sum())
                .build();
        return response;
    }

    private void modifyUserInfoResponseList(long id, List<UserInfoResponse> userInfoResponseList) {
        int index = 0;
        for (UserInfoResponse userInfoResponse : userInfoResponseList) {
            if (userInfoResponse.getUserId() == id) {
                userInfoResponse.updateMe();
                index = userInfoResponseList.indexOf(userInfoResponse);
                if (!userInfoResponseList.isEmpty()) {
                    Collections.swap(userInfoResponseList, 0, index);
                }
                break;
            }
        }
    }

    public UUID saveTrip(long userId, CreateTripRequest createTripRequest) {
        Trip trip = Trip.builder()
                .tripName(createTripRequest.getTripName())
                .startDate(createTripRequest.getStartDate())
                .endDate(createTripRequest.getEndDate())
                .inviteCode(createTripRequest.getEnterCode())
                .end(checkTripStatus(createTripRequest.getStartDate(), createTripRequest.getEndDate()))
                .ownerId(userId)
                .build();
        tripRepository.save(trip);
        return tripRepository.findIdByTripName(createTripRequest.getTripName(), userId);
    }

    public boolean checkTripStatus(LocalDate startDate, LocalDate endDate) {
        LocalDate localDate = LocalDate.now();
        if (localDate.isAfter(startDate) && localDate.isBefore(endDate)) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    public void checkTripAuth(long id, UUID tripId, String inviteCode) throws TripAccessDeniedException {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException(tripId + "값에 해당하는 여행이 없습니다."));
        Optional<UserTripMap> userTripMap = userTripMapRepository.findUserTripMapByUserAndTrip(id, trip.getTripId());
        if (trip.getInviteCode().equals(inviteCode) && userTripMap.isEmpty()) {
            enrollTrip(id, tripId);
        } else if (!trip.getInviteCode().equals(inviteCode)) {
            throw new TripAccessDeniedException(id);
        }
    }

    @Transactional
    public void enrollTrip(long id, UUID tripId) {
        userTripMapService.saveMap(id, tripId);
    }

    @Transactional
    public void updateTripInfo(UUID tripId, UpdateTripRequest updateTripRequest) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException(tripId + "값에 해당하는 여행이 없습니다."));
        boolean tripStatus = checkTripStatus(updateTripRequest.getStartDate(), updateTripRequest.getEndDate());
        trip.updateTripInfo(updateTripRequest.getTripName(), updateTripRequest.getStartDate(), updateTripRequest.getEndDate(), tripStatus);
    }

    @Transactional
    public void exitTrip(long id, UUID tripId) {
        UserTripMap userTripMap = userTripMapRepository.findUserTripMapByUserAndTrip(id, tripId)
                .orElseThrow(() -> new TripNotFoundException(tripId + ", " + id + " 값에 해당하는 여행이 없습니다."));
        userTripMapRepository.delete(userTripMap);
    }

    @Transactional
    public void deleteTrip(UUID tripId) {
        List<UserTripMap> userTripMapList = userTripMapRepository.findAllEntityByTripId(tripId);
        userTripMapRepository.deleteAll(userTripMapList);
        tripRepository.deleteById(tripId);
    }

    public List<UserInfoResponse> getMember(long id, UUID tripId) {
        List<UserInfoResponse> responses = userTripMapRepository.findAllByTripId(tripId);
        modifyUserInfoResponseList(id, responses);
        return responses;
    }

    public boolean checkTripMember(long id, UUID tripId) throws TripAccessDeniedException {
        userTripMapRepository.findUserTripMapByUserAndTrip(id, tripId)
                .orElseThrow(() -> new TripAccessDeniedException(id));
        return true;
    }

    public TripInfoResponse getTripInfo(UUID tripId){
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException(tripId + "값에 해당하는 여행이 없습니다."));
        return TripInfoResponse.builder()
        .tripName(trip.getTripName())
        .startDate(trip.getStartDate())
        .endDate(trip.getEndDate())
        .memberCnt(tripRepository.findByTripId(tripId))
                .build();
    }
}
