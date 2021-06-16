package com.lubycon.ourney.domains.trip.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Getter
public class TripInfoResponse {
    @NotNull
    private String tripName;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    private long memberCnt;

    @Builder
    public TripInfoResponse(@NotNull String tripName, @NotNull LocalDate startDate, @NotNull LocalDate endDate, long memberCnt) {
        this.tripName = tripName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.memberCnt = memberCnt;
    }
}
