package com.example.demo.model;

import com.example.demo.model.base.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a User entity in the system.
 * Users can create notes and organize them with tags.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
@AttributeOverride(name ="id", column = @Column(name = "userid"))
public class User extends BaseEntity {
    private static final int MAX_CREDENTIAL_LENGTH = 255;

    /**
     * Unique username for login (required, unique)
     */
    @Column(name = "username",length = MAX_CREDENTIAL_LENGTH,nullable = false, unique = true)
    private String username;

    /**
     * User's email address (required, unique)
     */
    @Column(name = "email",length = MAX_CREDENTIAL_LENGTH,nullable = false, unique = true)
    private String email;

    /**
     * Encrypted password (BCrypt hash)
     */
    @Column(name = "password", length = MAX_CREDENTIAL_LENGTH,nullable = false)
    private String password; // BCrypt hash

    /**
     * Tags created by this user
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();
}
