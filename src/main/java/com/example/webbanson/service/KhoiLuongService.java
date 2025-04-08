package com.example.webbanson.service;

import com.example.webbanson.model.KhoiLuong;
import com.example.webbanson.repo.KhoiLuongRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KhoiLuongService {
    private final KhoiLuongRepo repo;

    public List<KhoiLuong> getAll() {
        return repo.findAll();
    }
    
    public List<KhoiLuong> getKhoiLuongByIdSanPhamAndIDMauSac(Integer id, Integer idMauSac) {
        return repo.getKhoiLuongByIdSanPhamAndIDMauSac(id, idMauSac);
    }
    
    public List<KhoiLuong> getAllKhoiLuongConSuDung(){
        return repo.getAllKhoiLuongConSuDung();
    }

    public Page<KhoiLuong> getAllKhoiLuongByIdDesc(Pageable pageable) {
        return repo.getAllKhoiLuongByIdDesc(pageable);
    }

    public Page<KhoiLuong> searchKhoiLuong(Integer tenSearch, Integer trangThaiSearch, Pageable pageable) {
        return repo.searchKhoiLuong(tenSearch, trangThaiSearch, pageable);
    }

    public Long countSearchKhoiLuong(Integer tenSearch, Integer trangThaiSearch, Pageable pageable) {
        return repo.searchKhoiLuong(tenSearch, trangThaiSearch, pageable).getTotalElements();
    }
    
    public boolean existsByTen(Integer ten) {
        return repo.existsKhoiLuongByTen(ten);
    }

    public void add(KhoiLuong khoiLuong) {
        repo.save(khoiLuong);
    }

    public void update(KhoiLuong khoiLuong) {
        repo.save(khoiLuong);
    }

    public KhoiLuong getOneSanPhamById(Integer id) {
        return repo.findById(id).get();
    }

    public List<KhoiLuong> getKhoiLuongByMauSacAndSanPham(Integer idMauSac, Integer idSanPham) {
        return repo.getKhoiLuongByMauSacAndSanPham(idMauSac, idSanPham);
    }
}
