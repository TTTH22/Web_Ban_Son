package com.example.webbanson.service;

import com.example.webbanson.model.DongSanPham;
import com.example.webbanson.repo.DongSanPhamRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DongSanPhamService {
    private final DongSanPhamRepo repo;

    public List<DongSanPham> getAll() {
        return repo.findAll();
    }

    public List<DongSanPham> getDongSanPhamConHoatDong() {
        return repo.getDongSanPhamConHoatDong();
    }
    
    public Page<DongSanPham> getAllDongSanPhamByIdDesc(Pageable pageable) {
        return repo.getAllDongSanPhamByIdDesc(pageable);
    }

    public Page<DongSanPham> searchDongSanPham(String tenSearch, Integer trangThaiSearch, Pageable pageable) {
        return repo.searchDongSanPham(tenSearch, trangThaiSearch, pageable);
    }

    public long countSearchDongSanPham(String tenSearch, Integer trangThaiSearch, Pageable pageable) {
        return repo.searchDongSanPham(tenSearch, trangThaiSearch, pageable).getTotalElements();
    }

    public void add(DongSanPham dongSanPham) {
        repo.save(dongSanPham);
    }

    public DongSanPham getOneDongSanPhamById(Integer id) {
        return repo.findById(id).get();
    }
}
