package com.dojagy.todaysave.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Long id;
    @Column(name = "canonical_link", unique = true, nullable = false, length = 2048) // 이 테이블에서 캐노니컬 링크는 유일해야 함
    private String canonicalLink;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name ="thumnail_url", length = 2048)
    private String thumbnailUrl;
}
