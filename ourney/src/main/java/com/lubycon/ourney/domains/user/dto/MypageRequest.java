package com.lubycon.ourney.domains.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;


@Getter
@NoArgsConstructor
public class MypageRequest {
    @Nullable
    private String nickName;
    @Nullable
    private String profile;

    @Builder
    public MypageRequest(String nickName, String profile){
        this.nickName = nickName;
        this.profile = profile;
    }

}
