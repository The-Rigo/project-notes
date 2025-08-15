package com.example.demo.model;

import com.example.demo.model.base.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Note entity in the system.
 * Notes can contain text content, be organized with tags.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "note")
@AttributeOverride(name ="id", column = @Column(name = "noteid"))
public class Note extends BaseEntity {
    private static final int MAX_TITLE_LENGTH = 255;
    private static final int MAX_CONTENT_LENGTH = 255;

    /**
     * The title of the note (required)
     */
    @Column(name = "title",length = MAX_TITLE_LENGTH,nullable = false)
    private String title;

    /**
     * The content of the note (required)
     */
    @Column(name = "content",length = MAX_CONTENT_LENGTH,nullable = false)
    private String content;

    /**
     * Flag indicating if the note is archived (required)
     */
    @Column(name = "archived",nullable = false)
    private Boolean archived;

    /**
     * Set of tags associated with this note
     */
    @ManyToMany
    @JoinTable(name = "tag_note",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();
}
