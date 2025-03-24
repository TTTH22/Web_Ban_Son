package com.example.webbanson.service;

import com.example.webbanson.model.KhoiLuong;
import com.example.webbanson.repo.KhoiLuongRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KhoiLuongService {
    private final KhoiLuongRepo repo;

    public List<KhoiLuong> getKhoiLuongByIdSanPhamChiTiet(Integer id) {
        return repo.getKhoiLuongByIdSanPhamChiTiet(id);
    }
}
