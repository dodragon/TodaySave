package com.dojagy.todaysave.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private Long id;
    private String memo;
    @Column(name = "shared_link")
    private String sharedLink;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "link_id", nullable = false)
    private Link link;
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContentTag> contentTags = new ArrayList<>();

    public void addTag(Tag tag) {
        ContentTag contentTag = ContentTag.builder()
                .content(this)
                .tag(tag)
                .build();
        this.contentTags.add(contentTag);
    }
}
