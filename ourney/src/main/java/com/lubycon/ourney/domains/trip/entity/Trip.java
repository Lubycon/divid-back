package com.lubycon.ourney.domains.trip.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
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

    @Column(nullable = false)
    private Long ownerId;

    @Builder
    public Trip(String tripName, Long ownerId, Date startDate, Date endDate){
        this.tripName = tripName;
        this.ownerId = ownerId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void update(String url){
        this.url = url;
    }

}
