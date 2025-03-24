package com.example.webbanson.service;

import com.example.webbanson.model.SanPhamAnh;
import com.example.webbanson.repo.AnhSanPhamRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnhSanPhamService {
    private final AnhSanPhamRepo repo;

    public List<SanPhamAnh> getAllByIdSanPham(Integer idSanPham) {
        return repo.getAnhByIdSanPhamAnh(idSanPham);
    }
}
