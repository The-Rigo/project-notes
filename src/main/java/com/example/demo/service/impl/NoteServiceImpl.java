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
    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note create(Note note) {
        if (note == null) {
            throw  new IllegalArgumentException("Note cannot be null");
        }
        return noteRepository.save(note);
    }

    @Override
    public Optional<Note> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return noteRepository.findById(id);
    }

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    @Override
    public Note update(Long id, Note noteDetails) {
        if (id == null || noteDetails == null) {
            throw new IllegalArgumentException("ID and note details cannot be null");
        }
        return noteRepository.findById(id)
                .map(existingNote -> {
                    existingNote.setTitle(noteDetails.getTitle());
                    existingNote.setContent(noteDetails.getContent());
                    existingNote.setArchived(noteDetails.getArchived());
                    return noteRepository.save(existingNote);
                })
                .orElseThrow(()-> new RuntimeException("Note not found with id: "+id));
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        noteRepository.deleteById(id);
    }
}
