package com.blogpost.blogpost.repository;

import com.blogpost.blogpost.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByCategoryId(Long categoryId);
    List<Blog> findByForumId(Long forumId);
}