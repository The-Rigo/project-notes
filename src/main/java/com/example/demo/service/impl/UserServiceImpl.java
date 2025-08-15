package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.interfaces.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND_MSG = "User not found with id: ";
    private static final String NULL_ARGUMENT_MSG = " cannot be null";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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
        validateNotNull(id, "ID");
        return userRepository.findById(id);
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
}
