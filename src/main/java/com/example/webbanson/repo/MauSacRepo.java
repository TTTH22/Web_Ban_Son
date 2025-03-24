package com.example.webbanson.repo;

import com.example.webbanson.model.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MauSacRepo extends JpaRepository<MauSac, Integer> {

    @Query("select spct.idMauSac from SanPhamChiTiet spct where spct.idSanPham.id = :id")
    List<MauSac> getMauSacByIdSanPhamChiTiet(@Param("id") Integer id);
}
