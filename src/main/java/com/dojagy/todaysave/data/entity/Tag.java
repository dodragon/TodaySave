package com.dojagy.todaysave.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    @Comment("태그 고유 ID")
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    @Comment("태그명")
    private String name;
}
