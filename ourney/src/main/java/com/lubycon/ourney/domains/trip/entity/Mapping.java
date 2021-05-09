package com.lubycon.ourney.domains.trip.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Entity
public class Mapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long tripId;

    @NotNull
    private boolean approve;

    @Builder
    public Mapping(Long userId, Long tripId, boolean approve){
        this.userId = userId;
        this.tripId = tripId;
        this.approve = approve;
    }
}
