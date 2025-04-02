package com.example.webbanson.service;

import com.example.webbanson.model.MauSac;
import com.example.webbanson.repo.MauSacRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<MauSac> getAllMauSacByIdDesc(Pageable pageable){
        return repo.getAllMauSacByIdDesc(pageable);
    }

    public Page<MauSac> searchMauSac(String tenSearch, String maSearch, Integer trangThaiSearch, Pageable pageable){
        return repo.searchMauSac(tenSearch, maSearch, trangThaiSearch, pageable);
    }

    public long countSearchMauSac(String tenSearch, String maSearch, Integer trangThaiSearch, Pageable pageable){
        return repo.searchMauSac(tenSearch, maSearch, trangThaiSearch, pageable).getTotalElements();
    }

    public MauSac getById(Integer id) {
        return repo.findById(id).get();
    }

    public void add(MauSac mauSac) {
        repo.save(mauSac);
    }

    public MauSac getOneMauSacById(Integer id) {
        return repo.findById(id).get();
    }

    public List<MauSac> getAllConSuDung() {
        return repo.getAllConSuDung();
    }
}
