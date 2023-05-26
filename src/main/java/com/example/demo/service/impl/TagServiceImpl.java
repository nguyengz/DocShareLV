package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Tag;
import com.example.demo.repository.TagRepository;
import com.example.demo.service.ITagService;

@Service
public class TagServiceImpl implements ITagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public Optional<Tag> findByTagName(String tagName){
        return tagRepository.findByTagName(tagName);
    } 

    @Override
    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> search(String keyword) {
        // TODO Auto-generated method stub
        if (keyword != null) {
            List<Tag> tags=tagRepository.search(keyword);
            return tags;
        }
            return tagRepository.findAll();
    
    }

    
}
