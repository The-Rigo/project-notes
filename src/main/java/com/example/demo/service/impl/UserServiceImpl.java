package com.example.demo.service.impl;

import com.example.demo.model.Tag;
import com.example.demo.model.User;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final String NULL_ARGUMENT_MSG = " cannot be null";
    private static final String USERNOTFOUND = "User not found";

    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TagRepository tagRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new user with encrypted password
     * @param user The user entity to be created
     * @return The persisted user entity
     * @throws IllegalArgumentException if user is null
     */
    @Override
    public User create(User user) {
        validateNotNull(user, "User");
        encryptUserPassword(user);
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by ID (read-only operation)
     * @param id The user ID to search for
     * @return Optional containing the user if found
     * @throws IllegalArgumentException if id is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return Optional.of(user);
    }

    /**
            * Retrieves all users (read-only operation)
     * @return List of all users
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Deletes a user by ID
     * @param id The ID of the user to delete
     * @throws IllegalArgumentException if id is null
     */
    @Override
    public void delete(Long id) {
        validateNotNull(id, "ID");
        userRepository.deleteById(id);
    }

    // Helper method to validate null arguments
    private void validateNotNull(Object argument, String argumentName) {
        if (argument == null) {
            throw new IllegalArgumentException(argumentName + NULL_ARGUMENT_MSG);
        }
    }
    // Helper method to encrypt user password
    private void encryptUserPassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    // Helper method to update user fields
    private User updateUserFields(User existingUser, User newDetails) {
        existingUser.setUsername(newDetails.getUsername());
        existingUser.setEmail(newDetails.getEmail());
        // Solo actualiza la contraseña si se proporciona una nueva
        if (newDetails.getPassword() != null && !newDetails.getPassword().isEmpty()) {
            encryptUserPassword(existingUser);
        }
        // Aquí se pueden añadir más campos según la entidad User
        return existingUser;
    }

    /**
     * Assigns tags to a specific user (owner of the relationship).
     * Transactional operation to ensure data consistency.
     *
     * @param userId ID of the user to receive tags
     * @param tagIds List of tag IDs to assign to the user
     * @return The updated user entity with new tags
     * @throws RuntimeException if user is not found
     */
    @Override
    @Transactional
    public User assignTag(Long userId, List<Long> tagIds) {
        // Find user or throw exception if not found
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(USERNOTFOUND));
        // Get all tags from provided IDs
        List<Tag> tags = tagRepository.findAllById(tagIds);
        // Set user reference on each tag (owner side of relationship)
        for (Tag tag : tags) {
            tag.setUser(user);
        }
        // Update user's tag collection and save
        user.setTags(tags);
        return userRepository.save(user);
    }

    /**
     * Retrieves all tags associated with a specific user.
     *
     * @param userId ID of the user to query tags for
     * @return List of tags belonging to the user
     * @throws IllegalArgumentException if userId is null
     */
    @Override
    public List<Tag> getTagsByUser(Long userId) {
        // Validate input
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        // Query repository for user's tags
        return tagRepository.findByUserId(userId);
    }
}
