package com.example.webbanson.service;

import com.example.webbanson.model.DanhGia;
import com.example.webbanson.repo.DanhGiaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DanhGiaService {
    private final DanhGiaRepo repo;

    public Double getSoSaoBySanPham(Integer idSanPham) {
        return repo.getSoSaoBySanPham(idSanPham);
    }

    public Integer getSoNguoiDanhGia(Integer idSanPham) {
        return repo.getSoNguoiDanhGia(idSanPham);
    }

    public void save(DanhGia danhGia){
        repo.save(danhGia);
    }
}
