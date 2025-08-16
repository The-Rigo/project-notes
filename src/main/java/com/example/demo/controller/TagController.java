package com.example.demo.controller;

import com.example.demo.model.Note;
import com.example.demo.model.Tag;
import com.example.demo.model.User;
import com.example.demo.service.interfaces.NoteService;
import com.example.demo.service.interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for managing tag operations.
 * Provides endpoints for basic tag CRUD operations.
 * Base URL: /api/v1/tags
 */
@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    /**
     * Constructor for dependency injection of TagService
     * @param tagService Service layer for tag operations
     */
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Creates a new tag
     * @param tag Tag object to be created (from request body)
     * @return ResponseEntity with created tag (201) or error (400) if name is missing
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createTag(@RequestBody Tag tag){
        if (tag.getName() == null || tag.getName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Note is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        Tag createdTag = tagService.create(tag);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    /**
     * Retrieves all tags
     * @return ResponseEntity with list of all tags (200)
     */
    @GetMapping
    public ResponseEntity<List<Tag>> getAllTag() {
        List<Tag> tags = tagService.findAll();
        return  new ResponseEntity<>(tags, HttpStatus.OK);
    }

    /**
     * Deletes a tag by ID
     * @param id Tag ID to delete
     * @return Empty response with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Assigns tags to a user
     * @param id User ID to receive tags
     * @param noteIDs List of tag IDs to assign
     * @return ResponseEntity with updated user (200)
     */
    @PostMapping("/{id}/notes")
    public ResponseEntity<Tag> assignTags(
            @PathVariable Long id,
            @RequestBody List<Long> noteIDs){
        return ResponseEntity.ok(tagService.assignNotes(id,noteIDs));
    }
}
