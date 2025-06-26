package com.dojagy.todaysave.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"parent", "children"})
@Entity(name = "Folder")
@Table(name = "folder")
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id", nullable = false)
    @Comment("폴더 고유 ID")
    private Long id;
    @Column(name = "folder_name", nullable = false)
    @Comment("폴더명")
    private String folderName;
    @Column(nullable = false)
    @Comment("폴더 색상")
    private String color;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @Comment("상위 폴더 고유 ID")
    private Folder parent;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Comment("소유 회원 고유 식별 ID")
    private User user;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Folder> children = new ArrayList<>();

    public void setParent(Folder parent) {
        this.parent = parent;
        if (parent != null) {
            parent.getChildren().add(this);
        }
    }
}
