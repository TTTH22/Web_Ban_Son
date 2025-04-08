package com.example.webbanson.service;

import com.example.webbanson.model.GioHangChiTiet;
import com.example.webbanson.repo.GioHangChiTietRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GioHangChiTietService {
    private final GioHangChiTietRepo repo;

    public List<GioHangChiTiet> GioHangChiTietByIdKhachHang(Integer idGioHang) {
        return repo.GioHangChiTietByIdKhachHang(idGioHang);
    }

    public GioHangChiTiet getById(Integer gioHangChiTietId) {
        return repo.findById(gioHangChiTietId).orElse(null);
    }

    public void save(GioHangChiTiet gioHangChiTiet) {
        repo.save(gioHangChiTiet);
    }

    public void delete(Integer gioHangChiTietId) {
        repo.deleteById(gioHangChiTietId);
    }
}
