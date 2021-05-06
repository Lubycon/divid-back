package com.lubycon.ourney.domains.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT U.id FROM User U WHERE U.kakaoId = :kakaoId")
    Long findIdByKakaoId(@Param("kakaoId") Long kakaoId);

}
