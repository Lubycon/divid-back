package com.lubycon.divid.domains.trip.dto;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Getter
public class CreateTripRequest {
    @NotNull
    private String tripName;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private String enterCode;
}
