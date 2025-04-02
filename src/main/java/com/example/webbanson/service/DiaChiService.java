package com.example.webbanson.service;

import com.example.webbanson.model.DiaChi;
import com.example.webbanson.repo.DiaChiRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaChiService {
    private final DiaChiRepo repo;

    public List<DiaChi> getAll() {
        return repo.findAll();
    }

    public DiaChi getDiaChiNhanHangById(Integer id) {
        return repo.getDiaChiNhanHangById(id);
    }
}
