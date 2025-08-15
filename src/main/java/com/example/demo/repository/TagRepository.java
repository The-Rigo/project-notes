package com.example.demo.repository;

import com.example.demo.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for managing Tag entity.
 * Provides basic CRUD operations and custom query methods.
 */
public interface TagRepository extends JpaRepository<Tag,Long> {
    @Query("SELECT t FROM Tag t WHERE t.user.id = :userId")
    List<Tag> findByUserId(@Param("userId") Long userId);
}
