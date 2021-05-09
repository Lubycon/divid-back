package com.lubycon.ourney.domains.trip.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("SELECT T.id FROM Trip T WHERE T.tripName = :tripName AND T.ownerId = :ownerId")
    Long findIdbyTripName(@Param("tripName") String tripName, @Param("ownerId") Long ownerId);

    @Query("SELECT T.id FROM Trip T WHERE T.tripName = :tripName AND T.ownerId = :ownerId")
    Long findAllById(@Param("tripName") String tripName, @Param("ownerId") Long ownerId);
}