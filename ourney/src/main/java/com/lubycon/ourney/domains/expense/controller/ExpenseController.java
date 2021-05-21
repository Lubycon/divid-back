package com.lubycon.ourney.domains.expense.controller;

import com.lubycon.ourney.common.ResponseMessages;
import com.lubycon.ourney.common.config.interceptor.LoginId;
import com.lubycon.ourney.common.error.SimpleSuccessResponse;
import com.lubycon.ourney.domains.expense.dto.CreateExpenseRequest;
import com.lubycon.ourney.domains.expense.service.ExpenseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/expenses")
@RestController
public class ExpenseController {
    private final ExpenseService expenseService;

    @ApiOperation("계산 내역 생성")
    @PostMapping("")
    public ResponseEntity<SimpleSuccessResponse> createExpense(
            @LoginId long id,
            @RequestBody CreateExpenseRequest createExpenseRequest
    ) {
        expenseService.saveExpense(id, createExpenseRequest);
        return ResponseEntity.ok(new SimpleSuccessResponse(ResponseMessages.SUCCESS_CREATE_EXPENSE));
    }
}
