package com.lubycon.divid.domains.user.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByKakaoId(Long kakaoId);

    @Query("SELECT U.id FROM User U WHERE U.kakaoId = :kakaoId")
    Long findIdByKakaoId(@Param("kakaoId") Long kakaoId);

    @Query("SELECT U.refreshToken FROM User U WHERE U.id = :id")
    String findRefreshTokenById(@Param("id") long id);


}
