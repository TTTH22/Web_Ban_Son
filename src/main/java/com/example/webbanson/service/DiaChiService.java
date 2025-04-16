package com.example.webbanson.service;

import com.example.webbanson.model.DiaChi;
import com.example.webbanson.repo.DiaChiRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaChiService {
    private final DiaChiRepo repo;

    public List<DiaChi> getAll() {
        return repo.findAll();
    }

    public DiaChi getOne(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public void save(DiaChi diaChi) {
        repo.save(diaChi);
    }

    public DiaChi getDiaChiNhanHangById(Integer id) {
        return repo.getDiaChiNhanHangById(id);
    }

    public List<DiaChi> getAllDiaChiNhanHangById(Integer id) {
        return repo.getAllDiaChiNhanHangById(id);
    }

    public void boTrangThaiTatCa() {
        List<DiaChi> all = repo.findAll();
        for (DiaChi dc : all) {
            dc.setTrangThai(false);
        }
        repo.saveAll(all);
    }

    public boolean capNhatTrangThai(Integer id, boolean trangThai) {
        Optional<DiaChi> optional = repo.findById(id);
        if (optional.isPresent()) {
            DiaChi dc = optional.get();
            dc.setTrangThai(trangThai);
            repo.save(dc);
            return true;
        }
        return false;
    }

}
