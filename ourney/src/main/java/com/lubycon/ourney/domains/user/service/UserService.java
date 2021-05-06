package com.lubycon.ourney.domains.user.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lubycon.ourney.domains.user.dto.TokenResponse;
import com.lubycon.ourney.domains.user.domain.User;
import com.lubycon.ourney.domains.user.domain.UserRepository;
import com.lubycon.ourney.domains.user.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    public TokenResponse login(LoginRequest request) {
        if (!userRepository.findByEmail(request.getEmail()).isEmpty()) { // 이미 가입된 회원 -> 바로 토큰 전달
            Long userId = userRepository.findIdByKakaoId(request.getKakaoId());
            String token = jwtUtil.createToken(userId);
            String refreshToken = jwtUtil.createRefreshToken(userId);
            return new TokenResponse(userId, token, refreshToken);
        } else { // 신규 회원 가입 -> 입력 값 저장 후 토큰 전달
            User user = User.builder()
                    .kakaoId(request.getKakaoId())
                    .name(request.getName())
                    .email(request.getEmail())
                    .profileImg(request.getProfileImg())
                    .build();
            userRepository.save(user);

            Long userId = userRepository.findIdByKakaoId(request.getKakaoId());
            String token = jwtUtil.createToken(userId);
            String refreshToken = jwtUtil.createRefreshToken(userId);

            return new TokenResponse(userId, token, refreshToken);
        }
    }

    public LoginRequest getUserInfo(String accessToken) {
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        Long   kakaoId = 0L;
        String nickname = "";
        String email = "";
        String profileImg = "";
        String img[] = {"mouse","dog","cat","bear","rabbit"}; // profile 이미지 랜덤
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            //    요청에 필요한 Header에 포함될 내용
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
            nickname = properties.getAsJsonObject().get("nickname").getAsString();
            email = kakao_account.getAsJsonObject().get("email").getAsString();
            profileImg = img[(int)((Math.random()*10000)%5)];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LoginRequest(kakaoId, email, nickname, profileImg);
    }

    @Transactional
    public void updateToken(TokenResponse response){
        User user = userRepository.findById(response.getId())
                .orElseThrow(()-> new IllegalArgumentException("해당 사용자가 없습니다. "+response.getId()));
        user.update(response.getAccessToken(), response.getRefreshToken());
    }
}
