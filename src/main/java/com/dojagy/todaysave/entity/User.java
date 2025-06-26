package com.dojagy.todaysave.entity;

import com.dojagy.todaysave.entity.value.Gender;
import com.dojagy.todaysave.entity.value.Sns;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "User")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Comment("유저 식별 고유 ID")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    @Comment("SNS 타입 (GOOGLE, KAKAO, NAVER)")
    private Sns sns;
    @Column(name = "sns_key", unique = true, nullable = false, length = 1024)
    @Comment("SNS 고유 키값")
    private String snsKey;
    @Column(unique = true, nullable = false, length = 100)
    @Comment("회원 이메일")
    private String email;
    @Column(unique = true, nullable = false, length = 30)
    @Comment("회원 닉네임")
    private String nickname;
    @Comment("회원 생년월일 yyyy-MM-dd")
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    @Comment("회원 성별 (MALE, FEMALE)")
    private Gender gender;
    @CreatedDate
    @Column(name= "create_dt", updatable = false)
    @Comment("가입 날짜 및 시간")
    private LocalDateTime createDt;
}
