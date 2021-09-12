package com.lubycon.ourney.domains.expense.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter
public class CalculateListDateResponse implements Comparator<CalculateListDateResponse>{
    @NotNull
    private LocalDate payDate;

    private long tripTotalPrice;
    @NotNull
    private List<CalculateListResponse> calculateListDetails;

    @Builder
    public CalculateListDateResponse(LocalDate payDate, long tripTotalPrice, List<CalculateListResponse> calculateListDetails){
        this.payDate = payDate;
        this.tripTotalPrice = tripTotalPrice;
        this.calculateListDetails = calculateListDetails;
    }

        @Override
        public int compare(CalculateListDateResponse o1, CalculateListDateResponse o2) {
            if (o1.getPayDate().isBefore(o2.getPayDate())) {
                return 1;
            } else if (o1.getPayDate().isAfter(o2.getPayDate())) {
                return -1;
            } else {
                return 0;
            }
        }
}

