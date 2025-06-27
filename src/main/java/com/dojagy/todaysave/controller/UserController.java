package com.dojagy.todaysave.controller;

import com.dojagy.todaysave.dto.Result;
import com.dojagy.todaysave.dto.TokenInfo;
import com.dojagy.todaysave.dto.user.*;
import com.dojagy.todaysave.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Result<LoginResponseDto> signUp(@Valid @RequestBody UserSignUpRequestDto requestDto) {
        return userService.signUp(requestDto);
    }

    @GetMapping("/check-nickname")
    public Result<Void> checkNickname(@RequestParam String nickname) {
        return userService.nicknameCheck(nickname);
    }

    @PostMapping("/login")
    public Result<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        return userService.login(false, requestDto);
    }

    @GetMapping("/test-token")
    public Result<TokenInfo> testToken() {
        return userService.testToken();
    }

    @GetMapping("/token-reissue")
    public Result<TokenInfo> reissueToken(@RequestBody TokenReissueRequestDto requestDto) {
        return userService.tokenReissue(requestDto);
    }

    @GetMapping("/my-info")
    public Result<UserResponseDto> myUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userService.userInfo(userPrincipal.id());
    }
}
