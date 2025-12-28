package com.blogpost.blogpost.repository;

import com.blogpost.blogpost.model.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Long> {
}
