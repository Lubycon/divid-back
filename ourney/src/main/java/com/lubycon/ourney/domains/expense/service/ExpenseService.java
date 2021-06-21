package com.lubycon.ourney.domains.expense.service;

import com.lubycon.ourney.domains.expense.dto.*;
import com.lubycon.ourney.domains.expense.entity.Expense;
import com.lubycon.ourney.domains.expense.entity.ExpenseDetail;
import com.lubycon.ourney.domains.expense.entity.ExpenseDetailRepository;
import com.lubycon.ourney.domains.expense.entity.ExpenseRepository;
import com.lubycon.ourney.domains.trip.entity.Trip;
import com.lubycon.ourney.domains.trip.entity.TripRepository;
import com.lubycon.ourney.domains.trip.exception.TripNotFoundException;
import com.lubycon.ourney.domains.user.entity.User;
import com.lubycon.ourney.domains.user.entity.UserRepository;
import com.lubycon.ourney.domains.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseDetailRepository expenseDetailRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    private Trip getTrip(UUID tripId){
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException(tripId + " 값에 해당하는 여행이 없습니다."));
    }
    public GetExpenseResponse getExpense(long id, UUID tripId, long expenseId) {
        Expense expense = expenseRepository.findExpenseByTripIdAndExpenseId(tripId, expenseId);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id + "에 해당하는 유저가 없습니다."));
        List<GetExpenseDetailResponse> expenseOneDetailResponses = expenseDetailRepository.findAllByExpenseId(expenseId);
        for (GetExpenseDetailResponse getExpenseDetailResponse : expenseOneDetailResponses) {
            if (getExpenseDetailResponse.getUserId() == id) {
                getExpenseDetailResponse.updateMe();
                break;
            }
        }
        return GetExpenseResponse.builder()
                .expenseId(expenseId)
                .payDate(expense.getPayDate())
                .totalPrice(expense.getTotalPrice())
                .title(expense.getTitle())
                .payerId(expense.getPayerId())
                .profileImg(user.getProfileImg())
                .nickName(user.getNickName())
                .individual(expense.isIndividual())
                .getExpenseDetails(expenseOneDetailResponses)
                .build();
    }

    public void saveExpense(long id, ExpenseRequest expenseRequest) {
        Expense expense = Expense.builder()
                .tripId(expenseRequest.getTripId())
                .payDate(expenseRequest.getPayDate())
                .totalPrice(expenseRequest.getTotalPrice())
                .title(expenseRequest.getTitle())
                .payerId(expenseRequest.getPayerId())
                .individual(expenseRequest.isIndividual())
                .build();
        expenseRepository.save(expense);
        saveExpenseDetail(expenseRequest.getExpenseDetails(), expenseRepository.getMaxExpenseId());
    }

    private void saveExpenseDetail(List<ExpenseDetailRequest> expenseDetailRequests, Long expenseId) {
        for (ExpenseDetailRequest expenseDetailRequest : expenseDetailRequests) {
            ExpenseDetail expenseDetail = ExpenseDetail.builder()
                    .expenseId(expenseId)
                    .userId(expenseDetailRequest.getUserId())
                    .price(expenseDetailRequest.getPrice())
                    .build();
            expenseDetailRepository.save(expenseDetail);
        }
    }

    @Transactional
    public void updateExpense(long id, UUID tripId, long expenseId, ExpenseRequest expenseRequest) {
        Expense expense = expenseRepository.findExpenseByTripIdAndExpenseId(tripId, expenseId);
        expense.updateExpense(expenseRequest.getPayerId(), expenseRequest.getPayDate(), expenseRequest.getTitle(), expenseRequest.getTotalPrice());

        List<ExpenseDetail> expenseDetails = expenseDetailRepository.findAllEntityByExpenseId(expenseId);
        expenseDetailRepository.deleteAll(expenseDetails);

        List<ExpenseDetailRequest> detailRequests = expenseRequest.getExpenseDetails();
        saveExpenseDetail(detailRequests, expenseId);
    }

    @Transactional
    public void deleteExpense(UUID tripId, long expenseId) {
        Expense expense = expenseRepository.findExpenseByTripIdAndExpenseId(tripId, expenseId);
        expenseRepository.delete(expense);

        List<ExpenseDetail> expenseDetails = expenseDetailRepository.findAllEntityByExpenseId(expenseId);
        expenseDetailRepository.deleteAll(expenseDetails);
    }

    public List<ExpenseListResponse> getExpenseList(long id, UUID tripId) {
        Trip trip = getTrip(tripId);
        List<LocalDate> dates = expenseRepository.getPayDateByTripId(tripId);
        List<ExpenseListResponse> responses = new ArrayList<>(dates.size());
        for(LocalDate date : dates) {
           responses.add(ExpenseListResponse.builder()
                    .tripTotalPrice(expenseRepository.getSumByTripId(tripId))
                    .payDate(date)
                    .detailResponses(getExpenseListElement(id, tripId, date))
                    .build());
        }

        responses.sort((o1, o2) -> {
            if (o1.getPayDate().isBefore(o2.getPayDate())) {
                return 1;
            } else if (o1.getPayDate().isAfter(o2.getPayDate())) {
                return -1;
            } else {
                return 0;
            }
        });
        return responses;
    }

    public List<ExpenseListElementResponse> getExpenseListElement(long id, UUID tripId, LocalDate date) {
        List<ExpenseListElementResponse> responses = expenseDetailRepository.findAllByTripIdAndPayDate(tripId, date);
        if (!responses.isEmpty()) {
            for (ExpenseListElementResponse response : responses) {
                if (response.getUserId() == id) {
                    response.updateMe();
                }
            }
        }
        return responses;
    }

    public List<CalculateListDateResponse> getCalculateList(long id, UUID tripId) {
        Trip trip = getTrip(tripId);
        List<LocalDate> dates = expenseRepository.getPayDateByTripId(tripId);
        List<CalculateListDateResponse> dateResponses = new ArrayList<>();
        for(LocalDate date : dates) {
            List<CalculateListResponse> calculateListResponses = new ArrayList<>();
            List<ExpenseListElementResponse> responses = expenseDetailRepository.findAllByTripIdAndPayDate(tripId, date);
            for(ExpenseListElementResponse response : responses) {
                calculateListResponses.add(CalculateListResponse.builder()
                        .id(response.getUserId())
                        .totalPrice(response.getTotalPrice())
                        .title(response.getTitle())
                        .profileImg(response.getProfileImg())
                        .nickName(response.getNickName())
                        .calculateListDetails(expenseDetailRepository.findCalculateAllByTripIdAndPayDate(tripId, date))
                        .build());
            }
            dateResponses.add(CalculateListDateResponse.builder()
                    .payDate(date)
                    .calculateListDetails(calculateListResponses)
                    .build());
        }
        for(CalculateListDateResponse response : dateResponses) {
            modifyCalculateListElement(response.getCalculateListDetails(), id);
        }

        dateResponses.sort((o1, o2) -> {
            if (o1.getPayDate().isBefore(o2.getPayDate())) {
                return 1;
            } else if (o1.getPayDate().isAfter(o2.getPayDate())) {
                return -1;
            } else {
                return 0;
            }
        });

        return dateResponses;
    }

    public List<CalculateListResponse> modifyCalculateListElement(List<CalculateListResponse> calculateListResponses, long id) {
        if (!calculateListResponses.isEmpty()) {
            for (CalculateListResponse response : calculateListResponses) {
                if (response.getId() == id) {
                    response.updateMe();
                }
                for (CalculateListDetail detail : response.getCalculateListDetails()) {
                    detail.check(id, detail.getPayerId(), detail.getUserId());
                }
            }
        }

        return calculateListResponses;
    }

    public CalculateSummaryResponse getCalculateSummary(long id, UUID tripId){
        List<CalculateSummaryDetail> details = expenseDetailRepository.findCalculateSummaryByTripId(tripId);
        List<CalculateSummaryDetail> responses = new ArrayList<>();
        HashMap<Long, Long> summary = new HashMap<>();
        for(CalculateSummaryDetail detail : details){
            if(detail.getPayerId() != id && detail.getUserId() == id){ // GIVE
                if(summary.containsKey(detail.getPayerId())){
                    summary.put(detail.getPayerId(), summary.get(detail.getPayerId())+detail.getPrice());
                }else{
                    summary.put(detail.getPayerId(), detail.getPrice());
                }
            }
            else if(detail.getPayerId() == id && detail.getUserId() != id){ // TAKE
                if(summary.containsKey(detail.getUserId())){
                    summary.put(detail.getUserId(), summary.get(detail.getUserId())-detail.getPrice());
                }else{
                    summary.put(detail.getUserId(), (-1)*detail.getPrice());
                }
            }
        }
        for(Map.Entry<Long, Long> entry : summary.entrySet()){
            User user = userRepository.findById(entry.getKey()).orElseThrow(() -> new UserNotFoundException(entry.getKey() + " 값에 해당하는 여행이 없습니다."));
            responses.add(CalculateSummaryDetail.builder()
                    .profileImg(user.getProfileImg())
                    .nickName(user.getNickName())
                    .userId(id)
                    .price(Math.abs(summary.get(entry.getKey())))
                    .build());
        }

        return CalculateSummaryResponse.builder()
                .nickName(userRepository.findById(id).get().getNickName())
                .detailList(responses)
                .build();
    }


}
