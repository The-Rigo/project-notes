package com.example.demo.service.interfaces;

import com.example.demo.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    Tag create(Tag tag);
    Optional<Tag> findById(Long id);
    List<Tag> findAll();
    Tag update(Long id, Tag tagDetails);
    void delete(Long id);
}
