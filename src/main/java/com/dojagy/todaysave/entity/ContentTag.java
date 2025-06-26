package com.dojagy.todaysave.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"content", "tag"})
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "content_tag")
public class ContentTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_tag_id")
    private Long id;

    // Content와의 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    // Tag와의 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    // 이 연결 엔티티의 장점: 태그가 추가된 시간 등 추가 정보를 저장할 수 있습니다.
    @CreatedDate
    @Column(name = "create_dt", updatable = false)
    private LocalDateTime createdAt;

    // == 연관관계 편의 메소드 == //
    // Content에 ContentTag를 설정할 때, ContentTag에도 Content를 설정해주는 것이 좋습니다.
    public void setContent(Content content) {
        this.content = content;
        content.getContentTags().add(this);
    }
}
