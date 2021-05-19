package com.lubycon.ourney.domains.trip.entity;

import com.lubycon.ourney.domains.user.dto.UserInfoRequest;
import com.lubycon.ourney.domains.user.dto.UserInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTripMapRepository extends JpaRepository<UserTripMap, Long> {
    @Query("SELECT m " +
            "FROM UserTripMap m " +
            "WHERE m.user.id = :userId " +
            "AND m.trip.tripId = :tripId")
    Optional<UserTripMap> findUserTripMapByTripAndUser (@Param("userId") Long userId, @Param("tripId") UUID tripId);

    @Query("SELECT new com.lubycon.ourney.domains.user.dto.UserInfoResponse(M.user.nickName, M.user.profileImg)" +
            "FROM UserTripMap M " +
            "WHERE M.trip.tripId = :tripId")
    List<UserInfoResponse> findAllByTripId(@Param("tripId") UUID tripId);
}
