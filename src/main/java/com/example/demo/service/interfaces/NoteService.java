package com.example.demo.service.interfaces;

import com.example.demo.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    Note create(Note note);
    Optional<Note> findById(Long id);
    List<Note> findAll();
    Note update(Long id, Note noteDetails);
    void delete(Long id);
}
