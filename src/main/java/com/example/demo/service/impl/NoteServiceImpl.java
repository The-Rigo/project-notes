package com.example.demo.service.impl;

import com.example.demo.model.Note;
import com.example.demo.repository.NoteRepository;
import com.example.demo.service.interfaces.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {
    private static final String NOTE_NOT_FOUND_MSG = "Note not found with id: ";
    private static final String NULL_ARGUMENT_MSG = " cannot be null";
    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Creates a new note in the system.
     * @param note The note entity to be created
     * @return The persisted note entity
     * @throws IllegalArgumentException if note is null
     */
    @Override
    public Note create(Note note) {
        validateNotNull(note, "Note");
        return noteRepository.save(note);
    }

    /**
     * Retrieves a note by its unique identifier.
     * @param id The note ID to search for
     * @return Optional containing the note if found
     * @throws IllegalArgumentException if id is null
     */
    @Override
    public Optional<Note> findById(Long id) {
        validateNotNull(id, "ID");
        return noteRepository.findById(id);
    }

    /**
     * Retrieves all notes in the system.
     * @return List of all notes
     */
    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    /**
     * Updates an existing note with new details.
     * @param id The ID of the note to update
     * @param noteDetails The new note details
     * @return The updated note entity
     * @throws IllegalArgumentException if id or noteDetails are null
     * @throws RuntimeException if note with given id is not found
     */
    @Override
    public Note update(Long id, Note noteDetails) {
        validateNotNull(id, "ID");
        validateNotNull(noteDetails, "Note details");

        return noteRepository.findById(id)
                .map(existingNote -> updateNoteFields(existingNote, noteDetails))
                .orElseThrow(() -> new RuntimeException(NOTE_NOT_FOUND_MSG + id));
    }

    /**
     * Deletes a note by its ID.
     * @param id The ID of the note to delete
     * @throws IllegalArgumentException if id is null
     */
    @Override
    public void delete(Long id) {
        validateNotNull(id, "ID");
        noteRepository.deleteById(id);
    }
    // Helper method to validate null arguments
    private void validateNotNull(Object argument, String argumentName) {
        if (argument == null) {
            throw new IllegalArgumentException(argumentName + NULL_ARGUMENT_MSG);
        }
    }

    // Helper method to update note fields
    private Note updateNoteFields(Note existingNote, Note newDetails) {
        existingNote.setTitle(newDetails.getTitle());
        existingNote.setContent(newDetails.getContent());
        existingNote.setArchived(newDetails.getArchived());
        return noteRepository.save(existingNote);
    }
}
