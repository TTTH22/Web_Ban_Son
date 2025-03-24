package com.example.webbanson.repo;

import com.example.webbanson.model.SanPhamChiTiet;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamChiTietRepo extends JpaRepository<SanPhamChiTiet, Integer> {

    @Query("select min(donGiaGiam) from SanPhamChiTiet")
    Float findMinDonGiaGiam();

    @Query("select max(donGiaGiam) from SanPhamChiTiet")
    Float findMaxDonGiaGiam();

    @Query("select spct from SanPhamChiTiet spct where spct.idSanPham.id = :id")
    List<SanPhamChiTiet> findBySanPhamId(@Param("id") Integer id);
}
