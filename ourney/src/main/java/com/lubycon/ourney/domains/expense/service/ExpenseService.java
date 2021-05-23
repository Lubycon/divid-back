package com.lubycon.ourney.domains.expense.service;

import com.lubycon.ourney.domains.expense.dto.*;
import com.lubycon.ourney.domains.expense.entity.Expense;
import com.lubycon.ourney.domains.expense.entity.ExpenseDetail;
import com.lubycon.ourney.domains.expense.entity.ExpenseDetailRepository;
import com.lubycon.ourney.domains.expense.entity.ExpenseRepository;
import com.lubycon.ourney.domains.user.entity.User;
import com.lubycon.ourney.domains.user.entity.UserRepository;
import com.lubycon.ourney.domains.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        Long expenseId = expenseRepository.findIdByTitleAndPayDate(expenseRequest.getTitle(), expenseRequest.getPayDate());
        List<ExpenseDetailRequest> list = expenseRequest.getExpenseDetails();
        saveExpenseDetail(id, expenseRequest.getExpenseDetails(), expenseId);
    }

    private void saveExpenseDetail(long id, List<ExpenseDetailRequest> expenseDetailRequests, Long expenseId) {
        for(ExpenseDetailRequest expenseDetailRequest : expenseDetailRequests) {
            ExpenseDetail expenseDetail = ExpenseDetail.builder()
                    .expenseId(expenseId)
                    .userId(expenseDetailRequest.getUserId())
                    .price(expenseDetailRequest.getPrice())
                    .build();
            expenseDetailRepository.save(expenseDetail);
            if(expenseDetailRequest.getUserId() == id){
                expenseDetailRequest.updateMe();
            }
        }
    }

    @Transactional
    public void updateExpense(long id, UUID tripId, long expenseId, ExpenseRequest expenseRequest) {
        Expense expense = expenseRepository.findExpenseByTripIdAndExpenseId(tripId, expenseId);
        expense.updateExpense(expenseRequest.getPayerId(), expenseRequest.getPayDate(), expenseRequest.getTitle(), expenseRequest.getTotalPrice());

        List<ExpenseDetail> expenseDetails = expenseDetailRepository.findAllEntityByExpenseId(expenseId);
        expenseDetailRepository.deleteAll(expenseDetails);

        List<ExpenseDetailRequest> detailRequests = expenseRequest.getExpenseDetails();
        saveExpenseDetail(id, detailRequests, expenseId);
    }

    @Transactional
    public void deleteExpense(UUID tripId, long expenseId) {
        Expense expense = expenseRepository.findExpenseByTripIdAndExpenseId(tripId, expenseId);
        expenseRepository.delete(expense);

        List<ExpenseDetail> expenseDetails = expenseDetailRepository.findAllEntityByExpenseId(expenseId);
        expenseDetailRepository.deleteAll(expenseDetails);
    }


}
