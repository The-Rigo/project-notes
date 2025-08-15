package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing User entity.
 * Provides basic CRUD operations and custom query methods.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
