package com.lubycon.divid.domains.user.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lubycon.divid.common.Constants;
import com.lubycon.divid.common.ResponseMessages;
import com.lubycon.divid.domains.user.dto.LoginRequest;
import com.lubycon.divid.domains.user.dto.TokenResponse;
import com.lubycon.divid.domains.user.dto.UserInfoRequest;
import com.lubycon.divid.domains.user.entity.ProfileImg;
import com.lubycon.divid.domains.user.entity.User;
import com.lubycon.divid.domains.user.entity.UserRepository;
import com.lubycon.divid.domains.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RequiredArgsConstructor
@Service
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public TokenResponse login(LoginRequest request) {
        Long userId = userRepository.findIdByKakaoId(request.getKakaoId());
        return jwtService.issue(userId);
    }

    public TokenResponse signUp(LoginRequest request) {
        User user = User.builder()
                .kakaoId(request.getKakaoId())
                .nickName(request.getNickName())
                .email(request.getEmail())
                .profileImg(ProfileImg.getRandom().name())
                .build();
        userRepository.save(user);

        Long userId = userRepository.findIdByKakaoId(request.getKakaoId());
        return jwtService.issue(userId);
    }

    public LoginRequest getUserInfo(String accessToken) {
        String reqURL = Constants.KAKAO_OAUTH_URL;
        Long kakaoId = 0L;
        String nickName = "";
        String email = "";
        String profile = "";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            JsonElement element = JsonParser.parseString(result);
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            kakaoId = element.getAsJsonObject().get("id").getAsLong();
            nickName = properties.getAsJsonObject().get("nickname").getAsString();
            email = kakao_account.getAsJsonObject().get("email").getAsString();

            if (userRepository.existsByKakaoId(kakaoId)) {
                return new LoginRequest(kakaoId, email, nickName, profile, true);
            } else {
                return new LoginRequest(kakaoId, email, nickName, profile, false);
            }
        } catch (IOException e) {
            throw new UserNotFoundException("Access Token에 해당하는 정보가 없습니다.");
        }
    }

    @Transactional
    public void updateUser(long userId, UserInfoRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessages.NOT_EXIST_USER + userId));
        user.updateMyInfo(request.getNickName(), request.getProfileImg());
    }

    public UserInfoRequest getUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessages.NOT_EXIST_USER + userId));
        return new UserInfoRequest(user.getNickName(), user.getProfileImg());
    }

    @Transactional
    public void deleteUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessages.NOT_EXIST_USER + userId));
        userRepository.delete(user);
    }

}
