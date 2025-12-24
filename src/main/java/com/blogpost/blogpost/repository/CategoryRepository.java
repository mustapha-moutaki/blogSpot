package com.blogpost.blogpost.repository;

import com.blogpost.blogpost.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<String> findByName(String name);
    boolean existsByName(String name);
}