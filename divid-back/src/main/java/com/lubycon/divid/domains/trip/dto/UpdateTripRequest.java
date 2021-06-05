package com.lubycon.divid.domains.trip.dto;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;

@Getter
public class UpdateTripRequest {
    @Nullable
    private String tripName;
    @Nullable
    private LocalDate startDate;
    @Nullable
    private LocalDate endDate;

}
