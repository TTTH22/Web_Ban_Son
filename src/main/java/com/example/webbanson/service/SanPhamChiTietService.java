package com.example.webbanson.service;


import com.example.webbanson.model.SanPham;
import com.example.webbanson.model.SanPhamChiTiet;
import com.example.webbanson.repo.SanPhamChiTietRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SanPhamChiTietService {
    private final SanPhamChiTietRepo repo;

    public List<SanPhamChiTiet> getAll(){
        return repo.findAll();
    }

    public float minPrice(){
        return repo.findMinDonGiaGiam();
    }

    public float maxPrice(){
        return repo.findMaxDonGiaGiam();
    }

    public List<SanPhamChiTiet> getChiTietSabPhamByIdSanPham(Integer id){
        return repo.findBySanPhamId(id);
    }
}
