package com.lubycon.ourney.domains.trip.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Getter
@NoArgsConstructor
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String tripName;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(length= 100)
    private String url;

    private boolean endYn = false;

  //  @Column(nullable = false)
    private Long ownerIdx;

    @Builder
    public Trip(String tripName, Date startDate, Date endDate){
        this.tripName = tripName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
