package com.dojagy.todaysave.entity;

import com.dojagy.todaysave.entity.value.LinkType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "link")
@Getter
@NoArgsConstructor
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
    @Enumerated(EnumType.STRING)
    @Column(name = "link_type", length = 20)
    @Comment("링크 종류 (YOUTUBE, INSTAGRAM 등)")
    private LinkType linkType;
    @CreatedDate
    @Column(name= "create_dt", updatable = false)
    @Comment("생성 날짜 및 시간")
    private LocalDateTime createDt;

    @Builder
    public Link(String url, String title, String description, String thumbnailUrl, LinkType linkType) {
        this.canonicalLink = url;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.linkType = linkType; // 빌더에 추가
    }
}
