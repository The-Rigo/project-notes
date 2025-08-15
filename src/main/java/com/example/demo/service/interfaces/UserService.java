package com.example.demo.service.interfaces;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    void delete(Long id);
}
