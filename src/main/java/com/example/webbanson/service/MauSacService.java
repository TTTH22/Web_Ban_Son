package com.example.webbanson.service;

import com.example.webbanson.model.MauSac;
import com.example.webbanson.repo.MauSacRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MauSacService {
    private final MauSacRepo repo;

    public List<MauSac> getAll() {
        return repo.findAll();
    }

    public List<MauSac> getMauSacByIdSanPhamChiTiet(Integer id) {
        return repo.getMauSacByIdSanPhamChiTiet(id);
    }
}
