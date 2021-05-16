package com.lubycon.ourney.domains.user.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kakao_id", nullable = false)
    private Long kakaoId;

    @Column(name = "email", length = 45, nullable = false)
    private String email;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "nickname", nullable = false)
    private String nickName;

    @Column(name = "profile", nullable = false)
    private String profile;

    @Builder
    public User(Long kakaoId, String nickName, String email,String profile){
        this.kakaoId = kakaoId;
        this.nickName = nickName;
        this.email = email;
        this.profile = profile;
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void updateMyInfo(String nickName, String profile){
        this.nickName = nickName;
        this.profile = profile;
    }
}
