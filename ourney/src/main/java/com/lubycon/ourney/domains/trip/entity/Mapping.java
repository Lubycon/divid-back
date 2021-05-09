package com.lubycon.ourney.domains.trip.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Getter
@NoArgsConstructor
@Entity
public class Mapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long tripId;
    private boolean approve;

    @Builder
    public Mapping(Long userId, Long tripId, boolean approve){
        this.userId = userId;
        this.tripId = tripId;
        this.approve = approve;
    }
}
