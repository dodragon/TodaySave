package com.dojagy.todaysave.data.entity;

import com.dojagy.todaysave.data.entity.value.SharePermissionLevel;
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
@ToString(exclude = {"folder", "user"})
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "folder_share", uniqueConstraints = {
        // 한 유저는 한 폴더에 대해 하나의 공유 관계만 가질 수 있도록 유니크 제약조건 설정
        @UniqueConstraint(columnNames = {"folder_id", "user_id"})
})
public class FolderShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_share_id")
    @Comment("폴더 공유 고유 ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    @Comment("공유된 폴더")
    private Folder folder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("공유받은 사용자")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_level", nullable = false)
    @Comment("공유 권한 (VIEW, EDIT)")
    private SharePermissionLevel permissionLevel;

    @CreatedDate
    @Column(name = "shared_dt", updatable = false)
    @Comment("공유 시작 날짜 및 시간")
    private LocalDateTime sharedDt;
}
