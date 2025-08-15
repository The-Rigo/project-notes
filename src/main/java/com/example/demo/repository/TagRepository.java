package com.example.demo.repository;

import com.example.demo.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for managing Tag entity.
 * Provides basic CRUD operations and custom query methods.
 */
public interface TagRepository extends JpaRepository<Tag,Long> {
}
