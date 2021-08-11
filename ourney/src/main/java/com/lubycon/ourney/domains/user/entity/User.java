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

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(name = "email", length = 45, nullable = false)
    private String email;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "profile_img", nullable = false)
    private String profileImg;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTripMap> userTripMaps = new ArrayList<>();

    @Builder
    public User(String loginId, String name, String email, String profileImg){
        this.loginId = loginId;
        this.name = name;
        this.email = email;
        this.profileImg = profileImg;
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void updateMyInfo(String nickName, String profile){
        this.name = nickName;
        this.profileImg = profile;
    }
}
