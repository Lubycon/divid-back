package com.lubycon.ourney.domains.trip.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Entity
public class Trip {
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue
    private UUID tripId;

    @Column(name = "trip_name", length = 45, nullable = false)
    private String tripName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "invite_code", nullable = false)
    private String inviteCode;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "url")
    private String url;

    @Column(name = "end_yn")
    private boolean isEnded;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTripMap> userTripMaps = new ArrayList<>();

    @Builder
    public Trip(String tripName, Long ownerId, LocalDate startDate, LocalDate endDate, String inviteCode, boolean isEnded){
        this.tripName = tripName;
        this.ownerId = ownerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.inviteCode = inviteCode;
        this.isEnded = isEnded;
    }
    public void updateUrl(String url){
        this.url = url;
    }

}
