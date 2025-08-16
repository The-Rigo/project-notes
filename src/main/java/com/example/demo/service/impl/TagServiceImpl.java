package com.example.demo.service.impl;

import com.example.demo.model.Note;
import com.example.demo.model.Tag;
import com.example.demo.model.User;
import com.example.demo.repository.NoteRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.service.interfaces.TagService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class TagServiceImpl implements TagService {
    private static final String TAG_NOT_FOUND_MSG = "Tag not found with id: ";
    private static final String NULL_ARGUMENT_MSG = " cannot be null";
    private static final String USERNOTFOUND = "Tag not found";

    private final TagRepository tagRepository;
    private final NoteRepository noteRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, NoteRepository noteRepository) {
        this.tagRepository = tagRepository;
        this.noteRepository = noteRepository;
    }

    /**
     * Creates a new tag in the system.
     * @param tag The tag entity to be created
     * @return The persisted tag entity
     * @throws IllegalArgumentException if tag is null
     */
    @Override
    public Tag create(Tag tag) {
        validateNotNull(tag, "Tag");
        return tagRepository.save(tag);
    }

    /**
     * Retrieves a tag by its unique identifier.
     * @param id The tag ID to search for
     * @return Optional containing the tag if found
     * @throws IllegalArgumentException if id is null
     */
    @Override
    public Optional<Tag> findById(Long id) {
        validateNotNull(id, "ID");
        return tagRepository.findById(id);
    }

    /**
     * Retrieves all tags in the system.
     * @return List of all tags
     */
    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    /**
     * Updates an existing tag with new details.
     * @param id The ID of the tag to update
     * @param tagDetails The new tag details
     * @return The updated tag entity
     * @throws IllegalArgumentException if id or tagDetails are null
     * @throws RuntimeException if tag with given id is not found
     */
    @Override
    public Tag update(Long id, Tag tagDetails) {
        validateNotNull(id, "ID");
        validateNotNull(tagDetails, "Tag details");
        return tagRepository.findById(id)
                .map(existingTag -> updateTagFields(existingTag, tagDetails))
                .orElseThrow(() -> new RuntimeException(TAG_NOT_FOUND_MSG + id));
    }

    /**
     * Deletes a tag by its ID.
     * @param id The ID of the tag to delete
     * @throws IllegalArgumentException if id is null
     */

    @Override
    public void delete(Long id) {
        validateNotNull(id, "ID");
        tagRepository.deleteById(id);
    }

    // Helper method to validate null arguments
    private void validateNotNull(Object argument, String argumentName) {
        if (argument == null) {
            throw new IllegalArgumentException(argumentName + NULL_ARGUMENT_MSG);
        }
    }

    // Helper method to update tag fields
    private Tag updateTagFields(Tag existingTag, Tag newDetails) {
        existingTag.setName(newDetails.getName());
        // Aquí se pueden añadir más campos si el Tag tiene más propiedades
        return tagRepository.save(existingTag);
    }

    @Override
    @Transactional
    public Tag assignNotes(Long tagId, List<Long> noteIds){
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new RuntimeException("Tag no encontrado con ID: " + tagId));
        List<Note> notes = noteRepository.findAllById(noteIds);
        if (notes.size() != noteIds.size()) {
            throw new EntityNotFoundException("Algunas notas no fueron encontradas");
        }
        notes.forEach(note -> {
            note.getTags().add(tag);  // Añadir tag a la nota
            tag.getNotes().add(note);  // Añadir nota al tag (opcional, depende de tu modelo)
        });

        noteRepository.saveAll(notes);
        return  tagRepository.save(tag);
    }
}
