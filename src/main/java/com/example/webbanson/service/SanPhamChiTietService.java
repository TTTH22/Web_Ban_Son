package com.example.webbanson.service;


import com.example.webbanson.model.Nsx;
import com.example.webbanson.model.SanPham;
import com.example.webbanson.model.SanPhamChiTiet;
import com.example.webbanson.repo.SanPhamChiTietRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<SanPhamChiTiet> getAllSanPhamChiTietByIdDesc(Pageable pageable) {
        return repo.getAllSanPhamChiTietByIdDesc(pageable);
    }

    public Page<SanPhamChiTiet> searchSPCT(Integer idSanPham, Integer idMauSac, Integer idKhoiLuong, Pageable pageable) {
        return repo.searchSPCT(idSanPham, idMauSac, idKhoiLuong, pageable);
    }

    public long countSearchSPCT(Integer idSanPham, Integer idMauSac, Integer idKhoiLuong, Pageable pageable) {
        return repo.searchSPCT(idSanPham, idMauSac, idKhoiLuong, pageable).getTotalElements();
    }

    public void add(@Valid SanPhamChiTiet spct) {
        repo.save(spct);
    }

    public SanPhamChiTiet getOneSanPhamChiTietById(Integer id) {
        return repo.findById(id).get();
    }
}
