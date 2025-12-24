package com.blogpost.blogpost.repository;

import com.blogpost.blogpost.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {

}
