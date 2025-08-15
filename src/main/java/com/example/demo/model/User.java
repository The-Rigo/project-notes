package com.example.demo.model;

import com.example.demo.model.base.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
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
    @Column(name = "username",length = 255,nullable = false, unique = true)
    private String username;

    @Column(name = "email",length = 255,nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 255,nullable = false)
    private String password; // BCrypt hash

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();
}
