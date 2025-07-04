package com.dojagy.todaysave.data.service;

import com.dojagy.todaysave.data.dto.Result;
import com.dojagy.todaysave.data.dto.TokenInfo;
import com.dojagy.todaysave.data.dto.user.*;
import com.dojagy.todaysave.dto.user.*;
import com.dojagy.todaysave.data.entity.User;
import com.dojagy.todaysave.data.mapper.UserMapper;
import com.dojagy.todaysave.data.repository.UserRepository;
import com.dojagy.todaysave.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Result<LoginResponseDto> signUp(UserSignUpRequestDto requestDto) {
        if(userRepository.findByEmailOrSnsKey(requestDto.getEmail(), requestDto.getSnsKey()).isPresent()) {
            return Result.FAILURE("이미 가입된 회원입니다.");
        }

        if(userRepository.findByNickname(requestDto.getNickname()).isPresent()) {
            return Result.FAILURE("중복된 닉네임 입니다.");
        }

        User newUser = userMapper.toEntity(requestDto);
        User savedUser = userRepository.save(newUser);

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail(requestDto.getEmail());
        loginRequestDto.setSnsKey(savedUser.getSnsKey());

        return login(true, loginRequestDto);
    }

    @Transactional
    public Result<Void> nicknameCheck(String nickname) {
        if(userRepository.findByNickname(nickname).isPresent()) {
            return Result.FAILURE("중복된 닉네임 입니다.");
        }

        return Result.SUCCESS("사용가능한 닉네임 입니다.");
    }

    @Transactional
    public Result<LoginResponseDto> login(boolean isJoin, LoginRequestDto requestDto) {
        User user = userRepository.findByEmailAndSnsKey(requestDto.getEmail(), requestDto.getSnsKey())
                .orElse(null);

        if(user == null) {
            return Result.FAILURE("가입되지 않은 회원입니다.");
        }

        UserPrincipal userPrincipal = new UserPrincipal(user.getId(), user.getEmail());
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        //    Principal: UserPrincipal 객체 (사용자 정보)
        //    Credentials: 비밀번호 (여기선 불필요하므로 null)
        //    Authorities: 권한 목록
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        UserResponseDto userResponseDto = userMapper.toResponseDto(user);

        LoginResponseDto loginDto = LoginResponseDto.builder()
                .tokenInfo(tokenInfo)
                .userInfo(userResponseDto)
                .build();

        String successMessage;
        if(isJoin) {
            successMessage = "회원가입이 완료되었습니다.";
        }else {
            successMessage = "로그인에 성공하였습니다.";
        }

        return Result.SUCCESS(successMessage, loginDto);
    }

    @Transactional
    public Result<UserResponseDto> userInfo(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            return Result.FAILURE("존재하지 않는 회원입니다.");
        }

        return Result.SUCCESS("회원정보 조회에 성공하였습니다.", userMapper.toResponseDto(user));
    }

    @Transactional
    public Result<TokenInfo> tokenReissue(TokenReissueRequestDto requestDto) {
        if (!jwtTokenProvider.validateToken(requestDto.getRefreshToken())) {
            Result.FAILURE("유효하지 않은 Refresh Token 입니다.");
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(requestDto.getAccessToken());
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return Result.SUCCESS("토큰 재발급에 성공하였습니다.", tokenInfo);
    }

    @Transactional
    public Result<TokenInfo> testToken() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("test", "test", null);
        return Result.SUCCESS("테스트 토큰 발급 완료", jwtTokenProvider.generateToken(authentication));
    }

    @Transactional // 이 어노테이션을 붙여야 메소드 종료 시 변경 감지(Dirty Checking)가 동작합니다.
    public Result<UserResponseDto> updateUserProfile(Long userId, UserUpdateDto updateDto) {
        // 1. DB에서 사용자 정보를 조회합니다.
        //    orElseThrow를 사용하여 사용자가 없으면 예외를 발생시킵니다.
        User user = userRepository.findById(userId).orElse(null);

        if(user == null) {
            return Result.FAILURE("사용자를 찾을 수 없습니다.");
        }

        // 2. DTO로부터 받은 값으로 엔티티의 상태를 변경합니다.
        //    JPA의 '변경 감지(Dirty Checking)' 기능 덕분에,
        //    조회한 엔티티의 setter 메소드를 호출하여 값을 바꾸기만 하면
        //    트랜잭션이 커밋될 때 UPDATE 쿼리가 자동으로 실행됩니다.
        user.setBirthday(updateDto.getBirthday());
        user.setGender(updateDto.getGender());

        // 3. userRepository.save(user)를 명시적으로 호출할 필요가 없습니다.
        //    하지만 명시적으로 호출해도 문제는 없습니다.

        // 4. 변경된 엔티티를 DTO로 변환하여 반환합니다.
        return Result.SUCCESS("사용자 정보를 수정했습니다.", userMapper.toResponseDto(user));
    }
}
