package com.example.webbanson.repo;

import com.example.webbanson.model.KhoiLuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhoiLuongRepo extends JpaRepository<KhoiLuong, Integer> {

    @Query("select spct.idKhoiLuong from SanPhamChiTiet spct where spct.idSanPham.id = :id")
    List<KhoiLuong> getKhoiLuongByIdSanPhamChiTiet(@Param("id") Integer id);
}
