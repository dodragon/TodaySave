package com.dojagy.todaysave.controller;

import com.dojagy.todaysave.data.dto.Result;
import com.dojagy.todaysave.data.dto.TokenInfo;
import com.dojagy.todaysave.data.dto.user.*;
import com.dojagy.todaysave.data.service.NicknameGeneratorService;
import com.dojagy.todaysave.data.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final NicknameGeneratorService nicknameGeneratorService;

    @PostMapping("/join")
    public Result<LoginResponseDto> signUp(@Valid @RequestBody UserSignUpRequestDto requestDto) {
        return userService.signUp(requestDto);
    }

    @GetMapping("/my-info")
    public Result<UserResponseDto> myUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userService.userInfo(userPrincipal.id());
    }

    @GetMapping("/check-nickname")
    public Result<Void> checkNickname(@RequestParam String nickname) {
        return userService.nicknameCheck(nickname);
    }

    @GetMapping("/random-nickname")
    public Result<String> randomNickname() {
        return nicknameGeneratorService.generateUniqueNickname();
    }

    @PostMapping("/login")
    public Result<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        return userService.login(false, requestDto);
    }

    @PostMapping("/token-reissue")
    public Result<TokenInfo> reissueToken(@RequestBody TokenReissueRequestDto requestDto) {
        return userService.tokenReissue(requestDto);
    }

    @GetMapping("/test-token")
    public Result<TokenInfo> testToken() {
        return userService.testToken();
    }

    @PatchMapping("/info-update")
    public Result<UserResponseDto> updateUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                  @RequestBody UserUpdateDto updateDto) {
        return userService.updateUserProfile(userPrincipal.id(), updateDto);
    }
}
