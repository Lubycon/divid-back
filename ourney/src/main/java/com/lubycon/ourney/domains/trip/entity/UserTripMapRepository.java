package com.lubycon.ourney.domains.trip.entity;

import com.lubycon.ourney.domains.user.dto.UserInfoResponse;
import com.lubycon.ourney.domains.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTripMapRepository extends JpaRepository<UserTripMap, Long> {
    @Query("SELECT m " +
            "FROM UserTripMap m " +
            "WHERE m.user.id = :userId " +
            "AND m.trip.tripId = :tripId")
    Optional<UserTripMap> findUserTripMapByUserAndTrip(@Param("userId") long userId, @Param("tripId") UUID tripId);

    @Query("SELECT new com.lubycon.ourney.domains.user.dto.UserInfoResponse(M.user.id, M.user.nickName, M.user.profileImg)" +
            "FROM UserTripMap M " +
            "WHERE M.trip.tripId = :tripId")
    List<UserInfoResponse> findAllByTripId(@Param("tripId") UUID tripId);

    @Query("SELECT m " +
            "FROM UserTripMap m " +
            "WHERE m.trip.tripId = :tripId ")
    List<UserTripMap> findAllEntityByTripId(@Param("tripId") UUID tripId);

}

