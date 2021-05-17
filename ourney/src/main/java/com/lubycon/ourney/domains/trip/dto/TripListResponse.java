package com.lubycon.ourney.domains.trip.dto;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class TripListResponse {
    @NotNull
    private UUID tripId;
    @NotNull
    private String tripName;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private boolean isEnd;
    @NotNull
    private Long memberCnt;

    @Builder
    public TripListResponse(UUID tripId, String tripName, LocalDate startDate, LocalDate endDate, boolean isEnd){
        this.tripId = tripId;
        this.tripName = tripName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isEnd = isEnd;
    }

    public void updateMemberCnt(Long memberCnt){
        this.memberCnt = memberCnt;
    }
}
