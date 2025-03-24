package com.example.webbanson.service;

import com.example.webbanson.model.DongSanPham;
import com.example.webbanson.repo.DongSanPhamRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DongSanPhamService {
    private final DongSanPhamRepo repo;

    public List<DongSanPham> getAll() {
        return repo.findAll();
    }
}
