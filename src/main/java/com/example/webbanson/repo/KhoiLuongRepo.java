package com.example.webbanson.repo;

import com.example.webbanson.model.KhoiLuong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhoiLuongRepo extends JpaRepository<KhoiLuong, Integer> {

    boolean existsKhoiLuongByTen(Integer ten);

    @Query("select spct.idKhoiLuong from SanPhamChiTiet spct where spct.idSanPham.id = :id and spct.idMauSac.id = :idMauSac")
    List<KhoiLuong> getKhoiLuongByIdSanPhamAndIDMauSac(@Param("id") Integer id,
                                                        @Param("idMauSac") Integer idMauSac);
    
    @Query("select khoiLuong from KhoiLuong khoiLuong where khoiLuong.trangThai = true")
    List<KhoiLuong> getAllKhoiLuongConSuDung();
    
    @Query("select khoiLuong from KhoiLuong khoiLuong order by khoiLuong.id desc")
    Page<KhoiLuong> getAllKhoiLuongByIdDesc(Pageable pageable);

    @Query(value = "SELECT kl FROM KhoiLuong kl WHERE " +
            "(:trangThaiSearch = 1 " +
            "OR (:trangThaiSearch = 2 AND kl.trangThai = true) " +
            "OR (:trangThaiSearch = 3 AND kl.trangThai = false)) and " +
            "(:tenSearch is null or kl.ten = :tenSearch)" ,
            countQuery = "SELECT COUNT(kl) FROM KhoiLuong kl WHERE " +
                    "(:trangThaiSearch = 1 " +
                    "OR (:trangThaiSearch = 2 AND kl.trangThai = true) " +
                    "OR (:trangThaiSearch = 3 AND kl.trangThai = false)) and " +
                    "(:tenSearch is null or kl.ten = :tenSearch)")
    Page<KhoiLuong> searchKhoiLuong(@Param("tenSearch") Integer tenSearch,
                                    @Param("trangThaiSearch") Integer trangThaiSearch,
                                    Pageable pageable);

    @Query("select spct.idKhoiLuong from SanPhamChiTiet spct where spct.idSanPham.id = :idSanPham and spct.idMauSac.id = :idMauSac")
    List<KhoiLuong> getKhoiLuongByMauSacAndSanPham(@Param("idMauSac") Integer idMauSac,
                                                @Param("idSanPham") Integer idSanPham);
}
