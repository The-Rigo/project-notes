package com.example.demo.service.impl;

import com.example.demo.model.Tag;
import com.example.demo.repository.TagRepository;
import com.example.demo.service.interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag create(Tag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Tag cannot be null");
        }
        return tagRepository.save(tag);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return tagRepository.findById(id);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag update(Long id, Tag tagDetails) {
        if (id == null || tagDetails == null) {
            throw new IllegalArgumentException("ID and tag details cannot be null");
        }
        return tagRepository.findById(id)
                .map(existingTag -> {
                    existingTag.setName("test");
                    return tagRepository.save(existingTag);
                })
                .orElseThrow(()-> new RuntimeException("Tag not found with id: "+id));
    }

    @Override
    public void delete(Long id) {

    }
}
