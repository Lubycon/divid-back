package com.lubycon.ourney.domains.expense.service;

import com.lubycon.ourney.domains.expense.dto.*;
import com.lubycon.ourney.domains.expense.entity.Expense;
import com.lubycon.ourney.domains.expense.entity.ExpenseDetail;
import com.lubycon.ourney.domains.expense.entity.ExpenseDetailRepository;
import com.lubycon.ourney.domains.expense.entity.ExpenseRepository;
import com.lubycon.ourney.domains.expense.exception.ExpenseNotFoundException;
import com.lubycon.ourney.domains.user.entity.User;
import com.lubycon.ourney.domains.user.entity.UserRepository;
import com.lubycon.ourney.domains.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseDetailRepository expenseDetailRepository;
    private final UserRepository userRepository;

    public GetExpenseResponse getExpense(long id, UUID tripId, long expenseId){
        Expense expense = expenseRepository.findExpenseByTripIdAndExpenseId(tripId, expenseId);
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id+"에 해당하는 유저가 없습니다."));
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
                .getExpenseDetails(expenseOneDetailResponses)
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
        List<CreateExpenseDetailRequest> createExpenseDetailRequests = createExpenseRequest.getExpenseDetails();
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
    public void deleteExpense(UUID tripId, long expenseId) {
        Expense expense = expenseRepository.findExpenseByTripIdAndExpenseId(tripId, expenseId);
        List<ExpenseDetail> expenseDetails = expenseDetailRepository.findAllEntityByExpenseId(expenseId);
        expenseRepository.delete(expense);
        expenseDetailRepository.deleteAll(expenseDetails);
    }


}
