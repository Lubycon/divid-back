package com.lubycon.ourney.domains.trip.dto;

import com.lubycon.ourney.domains.user.entity.User;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

@Getter
public class GetTripResponse {
    @NotNull
    private String tripName;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private List<User> members;
}
