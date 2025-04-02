package com.example.webbanson.service;

import com.example.webbanson.model.KhachHang;
import com.example.webbanson.model.Nsx;
import com.example.webbanson.repo.KhachHangRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KhachHangService {
    private final KhachHangRepo repo;

    public Page<KhachHang> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<KhachHang> searchKhachHang(String tenSearch, String sdtSearch, Pageable pageable) {
        return repo.searchKhachHang(tenSearch, sdtSearch, pageable);
    }

    public long countSearchKhachHang(String tenSearch, String sdtSearch, Pageable pageable) {
        return repo.searchKhachHang(tenSearch, sdtSearch, pageable).getTotalElements();
    }

    public KhachHang getOneKhachHangById(Integer id) {
        return repo.findById(id).get();
    }
}
