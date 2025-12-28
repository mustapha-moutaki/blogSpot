package com.blogpost.blogpost.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blogs")
public class Blog extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    // author
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // forum
    @ManyToOne
    @JoinColumn(name = "forum_id", nullable = false)
    private Forum forum;

    // category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // comments
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

}
