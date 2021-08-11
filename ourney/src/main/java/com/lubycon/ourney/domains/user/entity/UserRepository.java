package com.lubycon.ourney.domains.user.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT U.id FROM User U WHERE U.loginId = :googleId")
    Long findIdByGoogleId(@Param("googleId") String googleId);

    @Query("SELECT U.refreshToken FROM User U WHERE U.id = :id")
    String findRefreshTokenById(@Param("id") long id);

    @Query("SELECT U.id FROM User U WHERE U.loginId = :kakaoId")
    Long findIdByKakaoId(@Param("kakaoId") Long kakaoId);

    Boolean existsByLoginId(String loginId);
}
