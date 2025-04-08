package com.example.webbanson.repo;

import com.example.webbanson.model.GioHang;
import com.example.webbanson.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangRepo extends JpaRepository<GioHang, Integer> {
    GioHang findByIdKhachHang(KhachHang idKhachHang);
}
