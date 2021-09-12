package com.lubycon.ourney.domains.user.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lubycon.ourney.common.Constants;
import com.lubycon.ourney.common.ResponseMessages;
import com.lubycon.ourney.domains.user.dto.LoginRequest;
import com.lubycon.ourney.domains.user.dto.TokenResponse;
import com.lubycon.ourney.domains.user.dto.UserInfoRequest;
import com.lubycon.ourney.domains.user.entity.ProfileImg;
import com.lubycon.ourney.domains.user.entity.User;
import com.lubycon.ourney.domains.user.entity.UserRepository;
import com.lubycon.ourney.domains.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    public TokenResponse googleLogin(LoginRequest request) {
        Long userId = userRepository.findIdByGoogleId(request.getGoogleId());
        return jwtService.issue(userId);
    }
    public TokenResponse kakaoLogin(LoginRequest request) {
        Long userId = userRepository.findIdByGoogleId(request.getLoginId());
        return jwtService.issue(userId);
    }

    public TokenResponse googleSignUp(LoginRequest request) {
        User user = User.builder()
                .loginId(request.getGoogleId())
                .name(request.getName())
                .email(request.getEmail())
                .profileImg(ProfileImg.getRandom().name())
                .build();
        userRepository.save(user);

        Long userId = userRepository.findIdByGoogleId(request.getGoogleId());
        return jwtService.issue(userId);
    }
    public TokenResponse kakaoSignUp(LoginRequest request) {
        User user = User.builder()
                .loginId(request.getLoginId())
                .name(request.getName())
                .email(request.getEmail())
                .profileImg(ProfileImg.getRandom().name())
                .build();
        userRepository.save(user);

        Long userId = userRepository.findIdByGoogleId(request.getLoginId());
        return jwtService.issue(userId);
    }

    public TokenResponse google(LoginRequest request) {
        User user = User.builder()
                .loginId(request.getLoginId())
                .name(request.getName())
                .email(request.getEmail())
                .profileImg(ProfileImg.getRandom().name())
                .build();
        userRepository.save(user);

        Long userId = userRepository.findIdByGoogleId(request.getLoginId());
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
            if(!kakao_account.getAsJsonObject().get("email").getAsString().isEmpty()) {
                email = kakao_account.getAsJsonObject().get("email").getAsString();
            }else{
                email = kakaoId+"@noEmail.com";
            }

            if (userRepository.existsByLoginId(kakaoId.toString())) {
                return new LoginRequest(kakaoId.toString(), email, nickName, profile, true);
            } else {
                return new LoginRequest(kakaoId.toString(), email, nickName, profile, false);
            }

        } catch (IOException e) {
            throw new UserNotFoundException("Access Token에 해당하는 정보가 없습니다.");
        }
    }

    public boolean checkExistUser(LoginRequest login) {
       if(userRepository.findByEmail(login.getEmail()).isPresent()){
            return true;
       }
       else return false;
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
        return new UserInfoRequest(user.getName(), user.getProfileImg());
    }

    @Transactional
    public void deleteUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessages.NOT_EXIST_USER + userId));
        userRepository.delete(user);
    }

}
