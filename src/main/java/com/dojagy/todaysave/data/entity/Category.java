package com.dojagy.todaysave.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "user") // 순환 참조 방지를 위해 user 필드 제외
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    @Comment("카테고리 고유 식별 ID")
    private Long id;

    @Column(name = "name", nullable = false, length = 50, unique = true)
    @Comment("카테고리명 (전체 시스템에서 유일)")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Comment("최초 카테고리 생성자 (null일 경우 시스템 기본 카테고리)")
    private User user;

    @CreatedDate
    @Column(name= "create_dt", updatable = false)
    @Comment("생성 날짜 및 시간")
    private LocalDateTime createDt;
}
