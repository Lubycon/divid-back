package com.lubycon.ourney.domains.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class UserTripMapRequest {
    @NotNull
    private Long user;
    @NotNull
    private String trip;

}
