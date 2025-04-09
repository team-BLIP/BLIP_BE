package com.example.blip_be.domain.user.presentation;

import com.example.blip_be.domain.auth.presentation.dto.TokenResponse;
import com.example.blip_be.domain.user.domain.UserEntity;
import com.example.blip_be.domain.user.presentation.dto.request.LoginRequest;
import com.example.blip_be.domain.user.presentation.dto.request.SignUpRequest;
import com.example.blip_be.domain.user.presentation.dto.response.MyPageResponse;
import com.example.blip_be.domain.user.service.LoginService;
import com.example.blip_be.domain.user.service.MyPageService;
import com.example.blip_be.domain.user.service.SignUpService;
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

    @GetMapping("/mypage")
    @ResponseStatus(HttpStatus.OK)
    public MyPageResponse getMyPage(@AuthenticationPrincipal AuthDetails authDetails) {

        return myPageService.getMyPage(authDetails.getUser());
    }
}
