package com.dojagy.todaysave.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
// ToString에서 contentTags를 제외하여 순환 참조를 방지합니다.
@ToString(exclude = {"user", "folder", "link", "contentTags"})
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "content", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"folder_id", "link_id"})
})
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    @Comment("컨텐츠 고유 식별 ID")
    private Long id;
    @Column(name = "memo", length = 500)
    @Comment("메모")
    private String memo;
    @Column(name = "shared_link")
    @Comment("실제 공유한 링크")
    private String sharedLink;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("컨텐츠 소유 회원")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    @Comment("소속 폴더")
    private Folder folder;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "link_id", nullable = false)
    @Comment("링크 고유 정보")
    private Link link;
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    @Comment("태그들")
    private List<ContentTag> contentTags = new ArrayList<>();
    @CreatedDate
    @Column(name= "create_dt", updatable = false)
    @Comment("생성 날짜 및 시간")
    private LocalDateTime createDt;

    public void addTag(Tag tag) {
        ContentTag contentTag = ContentTag.builder()
                .content(this)
                .tag(tag)
                .build();
        this.contentTags.add(contentTag);
    }
}
