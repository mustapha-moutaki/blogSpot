package com.blogpost.blogpost.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String content;

    // author
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // blog
    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;
}
