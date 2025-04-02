package com.example.webbanson.repo;

import com.example.webbanson.model.KhoiLuong;
import com.example.webbanson.model.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MauSacRepo extends JpaRepository<MauSac, Integer> {

    @Query("select spct.idMauSac from SanPhamChiTiet spct where spct.idSanPham.id = :id")
    List<MauSac> getMauSacByIdSanPhamChiTiet(@Param("id") Integer id);

    @Query("select ms from MauSac ms order by ms.id desc")
    public Page<MauSac> getAllMauSacByIdDesc(Pageable pageable);

    @Query(value = "SELECT ms FROM MauSac ms WHERE " +
            "(:trangThaiSearch = 1 " +
            "OR (:trangThaiSearch = 2 AND ms.trangThai = true) " +
            "OR (:trangThaiSearch = 3 AND ms.trangThai = false)) and " +
            "(:tenSearch is null or ms.ten like %:tenSearch%) and " +
            "(:maSearch is null or ms.ma like %:maSearch%)",
            countQuery = "SELECT COUNT(ms) FROM MauSac ms WHERE " +
                    "(:trangThaiSearch = 1 " +
                    "OR (:trangThaiSearch = 2 AND ms.trangThai = true) " +
                    "OR (:trangThaiSearch = 3 AND ms.trangThai = false)) and " +
                    "(:tenSearch is null or ms.ten like %:tenSearch%) and " +
                    "(:maSearch is null or ms.ma like %:maSearch%)")
    Page<MauSac> searchMauSac(@Param("tenSearch") String tenSearch,
                                    @Param("maSearch") String maSearch,
                                    @Param("trangThaiSearch") Integer trangThaiSearch,
                                    Pageable pageable);

    @Query("select ms from MauSac ms where ms.trangThai = true")
    List<MauSac> getAllConSuDung();
}
