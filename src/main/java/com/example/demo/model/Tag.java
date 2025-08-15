package com.example.demo.model;

import com.example.demo.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a Tag entity in the system.
 * Tags are used to categorize and organize notes, and belong to specific users.
 */
@Getter
@Setter
@ToString(exclude = {"user", "notes"}) // Excluye relaciones para evitar recursión@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tag")
@AttributeOverride(name ="id", column = @Column(name = "tagid"))
public class Tag extends BaseEntity {

    /**
     * The name of the tag (required, unique per user)
     */
    @Column(nullable = false)
    private String name;

    /**
     * The user who owns this tag
     */
    @ManyToOne(fetch = FetchType.LAZY) // Performance Optimization
    @JoinColumn(name = "userid")
    @JsonIgnore
    private User user;

    /**
     * List of notes associated with this tag
     */
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)//Performance Optimization
    private List<Note> notes = new ArrayList<>();

}
