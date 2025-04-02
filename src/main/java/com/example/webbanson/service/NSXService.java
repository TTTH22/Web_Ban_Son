package com.example.webbanson.service;

import com.example.webbanson.model.Nsx;
import com.example.webbanson.repo.NSXRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NSXService {
    private final NSXRepo repo;

    public List<Nsx> getAll() {
        return repo.findAll();
    }

    public List<Nsx> getNSXConHoatDong() {
        return repo.getNSXConHoatDong();
    }

    public Page<Nsx> getAllNSXByIdDesc(Pageable pageable) {
        return repo.getAllNSXByIdDesc(pageable);
    }

    public Page<Nsx> searchNSX(String tenSearch, Integer trangThaiSearch, Pageable pageable) {
        return repo.searchNSX(tenSearch, trangThaiSearch, pageable);
    }

    public long countSearchNSX(String tenSearch, Integer trangThaiSearch, Pageable pageable) {
        return repo.searchNSX(tenSearch, trangThaiSearch, pageable).getTotalElements();
    }

    public void add(@Valid Nsx nsx) {
        repo.save(nsx);
    }

    public Nsx getOneNSxById(Integer id) {
        return repo.findById(id).get();
    }
}
