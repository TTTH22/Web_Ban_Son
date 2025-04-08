package com.example.webbanson.repo;

import com.example.webbanson.model.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GioHangChiTietRepo extends JpaRepository<GioHangChiTiet, Integer> {

    @Query(value="select gh from GioHangChiTiet gh where gh.idGioHang.id = :idGioHang")
    public List<GioHangChiTiet> GioHangChiTietByIdKhachHang(@Param("idGioHang") Integer idGioHang);
}
