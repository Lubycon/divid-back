package com.lubycon.ourney.domains.expense.service;

import com.lubycon.ourney.common.config.interceptor.LoginId;
import com.lubycon.ourney.domains.expense.dto.CreateExpenseDetailRequest;
import com.lubycon.ourney.domains.expense.dto.CreateExpenseRequest;
import com.lubycon.ourney.domains.expense.entity.Expense;
import com.lubycon.ourney.domains.expense.entity.ExpenseDetail;
import com.lubycon.ourney.domains.expense.entity.ExpenseDetailRepository;
import com.lubycon.ourney.domains.expense.entity.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseDetailRepository expenseDetailRepository;

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

}
