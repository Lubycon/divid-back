package com.lubycon.divid.domains.expense.controller;

import com.lubycon.divid.common.ResponseMessages;
import com.lubycon.divid.common.config.interceptor.LoginId;
import com.lubycon.divid.common.error.SimpleSuccessResponse;
import com.lubycon.divid.domains.expense.dto.ExpenseListResponse;
import com.lubycon.divid.domains.expense.dto.ExpenseRequest;
import com.lubycon.divid.domains.expense.service.ExpenseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/expenses")
@RestController
public class ExpenseController {
    private final ExpenseService expenseService;

    @ApiOperation("지출 내역 조회")
    @GetMapping("")
    public ResponseEntity getExpense(
            @LoginId long id,
            @RequestParam UUID tripId,
            @RequestParam Long expenseId
    ) {
        return ResponseEntity.ok(expenseService.getExpense(id, tripId, expenseId));
    }

    @ApiOperation("지출 내역 생성")
    @PostMapping("")
    public ResponseEntity<SimpleSuccessResponse> createExpense(
            @LoginId long id,
            @Valid @RequestBody ExpenseRequest expenseRequest
    ) {
        expenseService.saveExpense(id, expenseRequest);
        return ResponseEntity.ok(new SimpleSuccessResponse(ResponseMessages.SUCCESS_CREATE_EXPENSE));
    }

    @ApiOperation("지출 내역 수정")
    @PutMapping("")
    public ResponseEntity updateExpense(
            @LoginId long id,
            @RequestParam UUID tripId,
            @RequestParam Long expenseId,
            @RequestBody ExpenseRequest expenseRequest
    ) {
        expenseService.updateExpense(id, tripId, expenseId, expenseRequest);
        return ResponseEntity.ok(new SimpleSuccessResponse(ResponseMessages.SUCCESS_UPDATE_EXPENSE));
    }

    @ApiOperation("지출 내역 삭제")
    @DeleteMapping("")
    public ResponseEntity deleteExpense(
            @RequestParam UUID tripId,
            @RequestParam Long expenseId
    ) {
        expenseService.deleteExpense(tripId, expenseId);
        return ResponseEntity.ok(new SimpleSuccessResponse(ResponseMessages.SUCCESS_DELETE_EXPENSE));
    }

    @ApiOperation("지출 리스트 조회")
    @GetMapping("/all")
    public ResponseEntity<List<ExpenseListResponse>> getExpenseList(
            @LoginId long id,
            @RequestParam UUID tripId
    ) {
        return ResponseEntity.ok(expenseService.getExpenseList(id, tripId));
    }
}
