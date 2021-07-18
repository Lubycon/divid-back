package com.lubycon.ourney.domains.user.service;

import com.lubycon.ourney.common.ResponseMessages;
import com.lubycon.ourney.domains.user.dto.LoginRequest;
import com.lubycon.ourney.domains.user.dto.TokenResponse;
import com.lubycon.ourney.domains.user.dto.UserInfoRequest;
import com.lubycon.ourney.domains.user.entity.ProfileImg;
import com.lubycon.ourney.domains.user.entity.User;
import com.lubycon.ourney.domains.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    public TokenResponse login(LoginRequest request) {
        Long userId = userRepository.findIdByGoogleId(request.getGoogleId());
        return jwtService.issue(userId);
    }

    public TokenResponse signUp(LoginRequest request) {
        User user = User.builder()
                .googleId(request.getGoogleId())
                .name(request.getName())
                .email(request.getEmail())
                .profileImg(ProfileImg.getRandom().name())
                .build();
        userRepository.save(user);

        Long userId = userRepository.findIdByGoogleId(request.getGoogleId());
        return jwtService.issue(userId);
    }

    public boolean getUserInfo(LoginRequest login) {
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
