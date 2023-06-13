package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Package;
import com.example.demo.repository.PackageRepository;
import com.example.demo.service.PackageService;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    PackageRepository packageRepository;
    @Override
    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }
    @Override
    public Optional<Package> findById(Long id) {
        return packageRepository.findById(id);
    }

    
}
