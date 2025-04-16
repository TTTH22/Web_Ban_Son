package com.example.webbanson.service;

import com.example.webbanson.model.DanhGia;
import com.example.webbanson.model.SanPham;
import com.example.webbanson.repo.DanhGiaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DanhGiaService {
    private final DanhGiaRepo repo;

    public Double getSoSaoBySanPham(Integer idSanPham) {
        return repo.getSoSaoBySanPham(idSanPham);
    }

    public void save(DanhGia danhGia){
        repo.save(danhGia);
    }

    public void delete(DanhGia danhGia){
        repo.delete(danhGia);
    }

    public Long countDanhGiaChuaXacNhan(){
        return repo.countByTrangThai(false);
    }

    public Long countDanhGiaDaXacNhan(){
        return repo.countByTrangThai(true);
    }

    public List<DanhGia> getAllDanhGiaChuaXacNhan(){
        return repo.findAllByTrangThai(false);
    }

    public List<DanhGia> getAllDanhGiaDaXacNhan(){
        return repo.findAllByTrangThai(true);
    }

    public List<SanPham> listSanPhamDaDanhGiaAll(){
        return repo.listSanPhamDaDanhGiaAll();
    }

    public List<DanhGia> findByFilter(Integer productId, Integer rating) {
        if (productId != null && rating != null) {
            return repo.findBySanPhamIdAndSoSao(productId, rating);
        } else if (productId != null) {
            return repo.findBySanPhamId(productId);
        } else if (rating != null) {
            return repo.findBySoSao(rating);
        } else {
            return repo.findAll();
        }
    }

    public DanhGia getById(Integer id) {
        return repo.findById(id).orElse(null);
    }
}
