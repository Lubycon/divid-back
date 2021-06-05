package com.lubycon.ourney.domains.user.entity;

import com.lubycon.ourney.domains.trip.entity.UserTripMap;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "kakao_id", nullable = false)
    private long kakaoId;

    @Column(name = "email", length = 45, nullable = false)
    private String email;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "nickname", nullable = false)
    private String nickName;

    @Column(name = "profile_img", nullable = false)
    private String profileImg;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTripMap> userTripMaps = new ArrayList<>();

    @Builder
    public User(long kakaoId, String nickName, String email, String profileImg){
        this.kakaoId = kakaoId;
        this.nickName = nickName;
        this.email = email;
        this.profileImg = profileImg;
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void updateMyInfo(String nickName, String profile){
        this.nickName = nickName;
        this.profileImg = profile;
    }
}
