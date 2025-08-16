package com.example.demo.controller;

import com.example.demo.model.Note;
import com.example.demo.service.interfaces.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
 * REST Controller for managing note operations.
 * Provides endpoints for basic CRUD operations on notes.
 * Base URL: /api/v1/notes
 */
@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {
    private final NoteService noteService;

    /**
     * Constructor for dependency injection of NoteService
     * @param noteService Service layer for note operations
     */
    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Creates a new note
     * @param note Note object to be created (from request body)
     * @return ResponseEntity with created note (201) or error (400) if title is missing
     */
    @Operation(summary = "Create a new note")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Note created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input (missing title)")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody Note note){
        // Validate required title field
        if (note.getTitle() == null || note.getTitle().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Note is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        Note createdNote = noteService.create(note);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }

    /**
     * Retrieves a note by ID
     * @param id Note ID to search for
     * @return ResponseEntity with note (200) or not found (404)
     */
    @Operation(summary = "Get a note by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note found"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        return  noteService.findById(id)
                .map(note -> new ResponseEntity<>(note,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all notes
     * @return ResponseEntity with list of all notes (200)
     */
    @Operation(summary = "Get all notes")
    @ApiResponse(responseCode = "200", description = "List of all notes")
    @GetMapping
    public ResponseEntity<List<Note>> getAllUser() {
        List<Note> notes = noteService.findAll();
        return  new ResponseEntity<>(notes, HttpStatus.OK);
    }

    /**
     * Retrieves a note by ID
     * @param id Note ID to search for
     * @return ResponseEntity with note (200) or not found (404)
     */
    @Operation(summary = "Update an existing note")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note updated successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateUser(
            @PathVariable Long id,
            @RequestBody Note noteDetails) {
        try {
            Note updatedNote = noteService.update(id, noteDetails);
            return new ResponseEntity<>(updatedNote, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Retrieves all notes
     * @return ResponseEntity with list of all notes (200)
     */
    @Operation(summary = "Delete a note")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Note deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
