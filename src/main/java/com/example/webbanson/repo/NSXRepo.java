package com.example.webbanson.repo;

import com.example.webbanson.model.DongSanPham;
import com.example.webbanson.model.MauSac;
import com.example.webbanson.model.Nsx;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NSXRepo extends JpaRepository<Nsx, Integer> {

    @Query("SELECT nsx FROM Nsx nsx WHERE nsx.trangThai = true ")
    public List<Nsx> getNSXConHoatDong();

    @Query("SELECT nsx FROM Nsx nsx order by nsx.id desc")
    public Page<Nsx> getAllNSXByIdDesc(Pageable pageable);

    @Query(value = "SELECT nsx FROM Nsx nsx WHERE " +
            "(:trangThaiSearch = 1 " +
            "OR (:trangThaiSearch = 2 AND nsx.trangThai = true) " +
            "OR (:trangThaiSearch = 3 AND nsx.trangThai = false)) and " +
            "(:tenSearch is null or nsx.ten like %:tenSearch%)",
            countQuery = "SELECT COUNT(nsx) FROM Nsx nsx WHERE " +
                    "(:trangThaiSearch = 1 " +
                    "OR (:trangThaiSearch = 2 AND nsx.trangThai = true) " +
                    "OR (:trangThaiSearch = 3 AND nsx.trangThai = false)) and " +
                    "(:tenSearch is null or nsx.ten like %:tenSearch%)")
    Page<Nsx> searchNSX(@Param("tenSearch") String tenSearch,
                                        @Param("trangThaiSearch") Integer trangThaiSearch,
                                        Pageable pageable);
}
