package com.lubycon.ourney.domains.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;


@Getter
@NoArgsConstructor
public class UserInfoRequest {
    @Nullable
    private String nickName;
    @Nullable
    private String profileImg;

    @Builder
    public UserInfoRequest(String nickName, String profileImg){
        this.nickName = nickName;
        this.profileImg = profileImg;
    }

}
