package com.example.demo.repository;

import com.example.demo.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for managing Note entity.
 * Provides basic CRUD operations and custom query methods.
 */
public interface NoteRepository extends JpaRepository<Note,Long> {
}
