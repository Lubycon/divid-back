package com.lubycon.ourney.domains.user.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long kakaoId;
    @Column(length = 45)
    private String email;
    private String refreshToken;
    private String name;
    private String profileImg;

    @Builder
    public User(Long kakaoId, String name, String email,String profileImg){
        this.kakaoId = kakaoId;
        this.name = name;
        this.email = email;
        this.profileImg = profileImg;
    }

    public void update(String refreshToken){
        this.refreshToken = refreshToken;
    }

}
