package com.lubycon.ourney.domains.trip.entity;

import com.lubycon.ourney.domains.expense.entity.Expense;
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

    @Column(name = "trip_index")
    private long tripIndex;

    @Column(name = "trip_name", length = 45, nullable = false)
    private String tripName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "invite_code", nullable = false)
    private String inviteCode;

    @Column(name = "owner_id", nullable = false)
    private long ownerId;

    @Column(name = "end")
    private boolean end;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTripMap> userTripMaps = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

    @Builder
    public Trip(long tripIndex, String tripName, long ownerId, LocalDate startDate, LocalDate endDate, String inviteCode, boolean end){
        this.tripIndex = tripIndex;
        this.tripName = tripName;
        this.ownerId = ownerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.inviteCode = inviteCode;
        this.end = end;
    }

    public void updateTripInfo(String tripName, LocalDate startDate, LocalDate endDate, boolean end){
        this.tripName = tripName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.end = end;
    }

}
