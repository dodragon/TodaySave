package com.dojagy.todaysave.entity;

import com.dojagy.todaysave.entity.value.Gender;
import com.dojagy.todaysave.entity.value.Sns;
import jakarta.persistence.*;
import lombok.*;
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
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Sns sns;
    @Column(name = "sns_key", unique = true, nullable = false, length = 1024)
    private String snsKey;
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Column(unique = true, nullable = false, length = 30)
    private String nickname;
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;
    @CreatedDate
    @Column(name= "create_dt", updatable = false)
    private LocalDateTime createDt;
}
