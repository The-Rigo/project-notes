package com.example.demo.controller;

import com.example.demo.model.Tag;
import com.example.demo.model.User;
import com.example.demo.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for managing user operations.
 * Provides endpoints for user CRUD operations and tag management.
 * Base URL: /api/v1/users
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    /**
     * Constructor for dependency injection of UserService
     * @param userService Service layer for user operations
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user
     * @param user User object to be created (from request body)
     * @return ResponseEntity with created user (201) or error (400)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody User user){
        // Validate required username field
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "User is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        User createdUser = userService.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Retrieves a user by ID
     * @param id User ID to search for
     * @return ResponseEntity with user (200) or not found (404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return  userService.findById(id)
                .map(user -> new ResponseEntity<>(user,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all users
     * @return ResponseEntity with list of all users (200)
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.findAll();
        return  new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Deletes a user by ID
     * @param id User ID to delete
     * @return Empty response with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Assigns tags to a user
     * @param id User ID to receive tags
     * @param tagIDs List of tag IDs to assign
     * @return ResponseEntity with updated user (200)
     */
    @PostMapping("/{id}/tags")
    public ResponseEntity<User> assignTags(
            @PathVariable Long id,
            @RequestBody List<Long> tagIDs){
        return ResponseEntity.ok(userService.assignTag(id,tagIDs));
    }

    /**
     * Retrieves all tags associated with a user
     * @param id User ID to query
     * @return ResponseEntity with list of tags (200)
     */
    @GetMapping("/{id}/tags")
    public ResponseEntity<List<Tag>> getRolesByUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getTagsByUser(id));
    }
}
