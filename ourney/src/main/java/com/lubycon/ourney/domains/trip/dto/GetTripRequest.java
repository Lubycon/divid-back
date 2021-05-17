package com.lubycon.ourney.domains.trip.dto;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class GetTripRequest {
    @NotNull
    private Long id;
    @NotNull
    private String code;
}
