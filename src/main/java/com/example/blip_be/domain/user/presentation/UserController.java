package com.example.blip_be.domain.user.presentation;

import com.example.blip_be.domain.auth.presentation.dto.TokenResponse;
import com.example.blip_be.domain.user.domain.UserEntity;
import com.example.blip_be.domain.user.presentation.dto.request.ChangePasswordRequest;
import com.example.blip_be.domain.user.presentation.dto.request.LoginRequest;
import com.example.blip_be.domain.user.presentation.dto.request.SignUpRequest;
import com.example.blip_be.domain.user.presentation.dto.response.MyPageResponse;
import com.example.blip_be.domain.user.service.*;
import com.example.blip_be.global.security.auth.AuthDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final SignUpService signUpService;
    private final LoginService loginService;
    private final MyPageService myPageService;
    private final ChangePasswordService changePasswordService;
    private final LogoutService logoutService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody SignUpRequest request) {
        signUpService.registerUser(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.login(request);
    }

    @DeleteMapping("/delete")
    public void logout() {
        logoutService.logout();
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        changePasswordService.changePassword(request);
    }

    @GetMapping("/{team-id}/mypage") // 수정 필요
    @ResponseStatus(HttpStatus.OK)
    public MyPageResponse getMyPageInTeam(@PathVariable("team-id") Long teamId) {
        return myPageService.getMyPage(teamId);
    }
}
