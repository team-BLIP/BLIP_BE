package com.example.blip_be.domain.user.presentation;

import com.example.blip_be.domain.auth.presentation.dto.TokenResponse;
import com.example.blip_be.domain.user.presentation.dto.request.LoginRequest;
import com.example.blip_be.domain.user.presentation.dto.request.SignUpRequest;
import com.example.blip_be.domain.user.service.LoginService;
import com.example.blip_be.domain.user.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final SignUpService signUpService;
    private final LoginService loginService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(
            @RequestPart("account_id") String accountId,
            @RequestPart("password") String password,
            @RequestPart("email") String email,
            @RequestPart("voice_data") MultipartFile voiceData
    ) {
        SignUpRequest request = new SignUpRequest(accountId, password, email, voiceData);
        signUpService.registerUser(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }
}
