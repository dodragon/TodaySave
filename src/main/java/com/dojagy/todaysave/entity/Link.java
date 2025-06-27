package com.dojagy.todaysave.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "link")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    @Comment("링크 고유 식별 ID")
    private Long id;
    @Column(name = "canonical_link", unique = true, nullable = false, length = 2048) // 이 테이블에서 캐노니컬 링크는 유일해야 함
    @Comment("캐노니컬링크")
    private String canonicalLink;
    @Column(nullable = false)
    @Comment("링크 타이틀")
    private String title;
    @Column(columnDefinition = "TEXT")
    @Comment("링크 Description")
    private String description;
    @Column(name ="thumnail_url", length = 2048)
    @Comment("링크 썸네일 URL")
    private String thumbnailUrl;
    @Column(name ="favicon_url", length = 2048)
    @Comment("파비콘 URL")
    private String faviconUrl;
    @CreatedDate
    @Column(name= "create_dt", updatable = false)
    @Comment("생성 날짜 및 시간")
    private LocalDateTime createDt;
}
