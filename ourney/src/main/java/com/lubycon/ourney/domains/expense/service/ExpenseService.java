package com.lubycon.ourney.domains.expense.service;

import com.lubycon.ourney.domains.expense.dto.*;
import com.lubycon.ourney.domains.expense.entity.Expense;
import com.lubycon.ourney.domains.expense.entity.ExpenseDetail;
import com.lubycon.ourney.domains.expense.entity.ExpenseDetailRepository;
import com.lubycon.ourney.domains.expense.entity.ExpenseRepository;
import com.lubycon.ourney.domains.trip.entity.UserTripMapRepository;
import com.lubycon.ourney.domains.user.dto.UserInfoResponse;
import com.lubycon.ourney.domains.user.entity.User;
import com.lubycon.ourney.domains.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseDetailRepository expenseDetailRepository;
    private final UserRepository userRepository;

    public GetExpenseOneResponse getExpenseOne(long id, UUID tripId, Long expenseId){
        Expense expense = expenseRepository.findExpenseByTripIdAndExpenseId(tripId, expenseId);
        Optional<User> user = userRepository.findById(id);
        List<GetExpenseOneDetailResponse> expenseOneDetailResponses = expenseDetailRepository.findAllByExpenseId(expenseId);
        for (GetExpenseOneDetailResponse getExpenseOneDetailResponse : expenseOneDetailResponses) {
            if (getExpenseOneDetailResponse.getUserId() == id) {
                getExpenseOneDetailResponse.updateMe();
                break;
            }
        }
        return GetExpenseOneResponse.builder()
                .expenseId(expenseId)
                .payDate(expense.getPayDate())
                .totalPrice(expense.getTotalPrice())
                .title(expense.getTitle())
                .payerId(expense.getPayerId())
                .profileImg(user.get().getProfileImg())
                .nickName(user.get().getNickName())
                .getExpenseOneDetailResponses(expenseOneDetailResponses)
                .build();
    }

    public void saveExpense(long id, CreateExpenseRequest createExpenseRequest) {
        Expense expense = Expense.builder()
                .tripId(createExpenseRequest.getTripId())
                .payDate(createExpenseRequest.getPayDate())
                .totalPrice(createExpenseRequest.getTotalPrice())
                .title(createExpenseRequest.getTitle())
                .payerId(createExpenseRequest.getPayerId())
                .build();
        expenseRepository.save(expense);
        Long expenseId = expenseRepository.findIdByTitleAndPayDate(createExpenseRequest.getTitle(), createExpenseRequest.getPayDate());
        List<CreateExpenseDetailRequest> createExpenseDetailRequests = createExpenseRequest.getCreateExpenseDetailList();
        for(CreateExpenseDetailRequest createExpenseDetailRequest : createExpenseDetailRequests) {
            ExpenseDetail expenseDetail = ExpenseDetail.builder()
                    .expenseId(expenseId)
                    .userId(createExpenseDetailRequest.getUserId())
                    .price(createExpenseDetailRequest.getPrice())
                    .build();
            expenseDetailRepository.save(expenseDetail);
            if(createExpenseDetailRequest.getUserId() == id){
                createExpenseDetailRequest.updateMe();
            }
        }
    }

    @Transactional
    public void deleteExpense(UUID tripId, Long expenseId) {
        Expense expense = expenseRepository.findExpenseByTripIdAndExpenseId(tripId, expenseId);
        List<ExpenseDetail> expenseDetails = expenseDetailRepository.findAllEntityByExpenseId(expenseId);
        expenseRepository.delete(expense);
        expenseDetailRepository.deleteAll(expenseDetails);
    }

}
