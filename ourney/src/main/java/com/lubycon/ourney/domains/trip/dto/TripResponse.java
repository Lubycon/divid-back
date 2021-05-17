package com.lubycon.ourney.domains.trip.dto;

import com.lubycon.ourney.domains.user.dto.UserInfoDto;
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
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private List<UserInfoDto> userInfoDtoList;

    @Builder
    public TripResponse(UUID tripId, String tripName, LocalDate startDate, LocalDate endDate, List<UserInfoDto> userInfoDtoList){
        this.tripId = tripId;
        this.tripName = tripName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userInfoDtoList = userInfoDtoList;
    }

}
