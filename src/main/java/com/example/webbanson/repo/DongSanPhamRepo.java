package com.example.webbanson.repo;

import com.example.webbanson.model.DongSanPham;
import com.example.webbanson.model.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DongSanPhamRepo extends JpaRepository<DongSanPham, Integer> {

    @Query("SELECT d FROM DongSanPham d WHERE d.trangThai = true ")
    List<DongSanPham> getDongSanPhamConHoatDong();

    @Query("select dsp from DongSanPham dsp order by dsp.id desc")
    public Page<DongSanPham> getAllDongSanPhamByIdDesc(Pageable pageable);

    @Query(value = "SELECT dsp FROM DongSanPham dsp WHERE " +
            "(:trangThaiSearch = 1 " +
            "OR (:trangThaiSearch = 2 AND dsp.trangThai = true) " +
            "OR (:trangThaiSearch = 3 AND dsp.trangThai = false)) and " +
            "(:tenSearch is null or dsp.ten like %:tenSearch%)",
            countQuery = "SELECT COUNT(dsp) FROM DongSanPham dsp WHERE " +
                    "(:trangThaiSearch = 1 " +
                    "OR (:trangThaiSearch = 2 AND dsp.trangThai = true) " +
                    "OR (:trangThaiSearch = 3 AND dsp.trangThai = false)) and " +
                    "(:tenSearch is null or dsp.ten like %:tenSearch%)")
    Page<DongSanPham> searchDongSanPham(@Param("tenSearch") String tenSearch,
                              @Param("trangThaiSearch") Integer trangThaiSearch,
                              Pageable pageable);
}
