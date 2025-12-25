package com.blogpost.blogpost.repository;

import com.blogpost.blogpost.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
   boolean existsById(Long id);
}
