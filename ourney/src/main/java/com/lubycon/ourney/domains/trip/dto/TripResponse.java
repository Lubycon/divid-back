package com.lubycon.ourney.domains.trip.dto;

import com.lubycon.ourney.domains.user.dto.UserInfoRequest;
import com.lubycon.ourney.domains.user.dto.UserInfoResponse;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
public class TripResponse {
    @NotNull
    private UUID tripId;
    @NotNull
    private String tripName;
    @NotNull
    private String inviteCode;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private List<UserInfoResponse> userInfoResponseList;

    @Builder
    public TripResponse(UUID tripId, String tripName, String inviteCode, LocalDate startDate, LocalDate endDate, List<UserInfoResponse> userInfoResponseList){
        this.tripId = tripId;
        this.tripName = tripName;
        this.inviteCode = inviteCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userInfoResponseList = userInfoResponseList;
    }

}
