package com.lubycon.ourney.domains.trip.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@Data
public class SaveTripRequest {
    private String tripNm;
    private Long ownerId;
    private Date startDate;
    private Date endDate;
}
