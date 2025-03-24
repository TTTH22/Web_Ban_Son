package com.example.webbanson.service;

import com.example.webbanson.model.Nsx;
import com.example.webbanson.repo.NSXRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NSXService {
    private final NSXRepo repo;

    public List<Nsx> getAll() {
        return repo.findAll();
    }
}
