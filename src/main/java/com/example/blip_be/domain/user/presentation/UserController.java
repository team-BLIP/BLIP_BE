package com.example.blip_be.domain.user.presentation;

import com.example.blip_be.domain.auth.presentation.dto.TokenResponse;
import com.example.blip_be.domain.user.presentation.dto.request.LoginRequest;
import com.example.blip_be.domain.user.presentation.dto.request.SignUpRequest;
import com.example.blip_be.domain.user.service.LoginService;
import com.example.blip_be.domain.user.service.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final SignUpService signUpService;
    private final LoginService loginService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
<<<<<<< HEAD
    public void signUp(@RequestBody SignUpRequest request) {
=======
    public void signUp(@RequestBody @Valid SignUpRequest request) {
>>>>>>> origin/featur/video-call
        signUpService.registerUser(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.login(request);
    }
}
