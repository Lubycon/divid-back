package com.lubycon.ourney.domains.expense.controller;

import com.lubycon.ourney.common.ResponseMessages;
import com.lubycon.ourney.common.config.interceptor.LoginId;
import com.lubycon.ourney.common.error.SimpleSuccessResponse;
import com.lubycon.ourney.domains.expense.dto.CreateExpenseRequest;
import com.lubycon.ourney.domains.expense.dto.ExpenseListInfoResponse;
import com.lubycon.ourney.domains.expense.dto.UpdateExpenseRequest;
import com.lubycon.ourney.domains.expense.service.ExpenseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/expenses")
@RestController
public class ExpenseController {
    private final ExpenseService expenseService;

    @ApiOperation("지출 내역 조회")
    @GetMapping("")
    public ResponseEntity getExpenseOne(
            @LoginId long id,
            @RequestParam UUID tripId,
            @RequestParam Long expenseId
    ){
        return ResponseEntity.ok(expenseService.getExpenseOne(id, tripId, expenseId));
    }

    @ApiOperation("지출 내역 생성")
    @PostMapping("")
    public ResponseEntity<SimpleSuccessResponse> createExpense(
            @LoginId long id,
            @RequestBody CreateExpenseRequest createExpenseRequest
    ) {
        expenseService.saveExpense(id, createExpenseRequest);
        return ResponseEntity.ok(new SimpleSuccessResponse(ResponseMessages.SUCCESS_CREATE_EXPENSE));
    }

    @ApiOperation("지출 내역 삭제")
    @DeleteMapping("")
    public ResponseEntity deleteExpenseOne(
            @RequestParam UUID tripId,
            @RequestParam Long expenseId
    ){
        expenseService.deleteExpense(tripId, expenseId);
        return ResponseEntity.ok(new SimpleSuccessResponse(ResponseMessages.SUCCESS_DETAIL_EXPENSE));
    }
}
