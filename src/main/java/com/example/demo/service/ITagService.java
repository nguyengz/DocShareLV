package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Tag;

public interface ITagService {
    Optional<Tag> findByTagName(String tagName);
    Tag save(Tag tag);
    List<Tag> search(String keyword);
}
