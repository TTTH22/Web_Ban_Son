package com.example.webbanson.repo;

import com.example.webbanson.model.Nsx;
import com.example.webbanson.model.SanPham;
import com.example.webbanson.model.SanPhamChiTiet;
import org.springframework.data.domain.Page;
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

    @Query("select spct from SanPhamChiTiet spct where spct.idSanPham.id = :id and " +
            "(:idMauSac is null or spct.idMauSac.id = :idMauSac) and " +
            "(:idKhoiLuong is null or spct.idKhoiLuong.id = :idKhoiLuong)")
    List<SanPhamChiTiet> getChiTietSabPhamByIdSanPhamIdMauSacIdKhoiLuong(@Param("id") Integer id,
                                                                        @Param("idMauSac") Integer mauSac,
                                                                        @Param("idKhoiLuong") Integer khoiLuong);


    @Query("select spct from SanPhamChiTiet spct order by spct.id desc")
    Page<SanPhamChiTiet> getAllSanPhamChiTietByIdDesc(Pageable pageable);

    @Query(value="select spct from SanPhamChiTiet spct where" +
            " (spct.idSanPham.id = :idSanPham or :idSanPham is null) " +
            "and (spct.idMauSac.id = :idMauSac or :idMauSac is null) " +
            "and (spct.idKhoiLuong.id = :idKhoiLuong or :idKhoiLuong is null) " ,
            countQuery = "select COUNT(spct) from SanPhamChiTiet spct where" +
                    "(spct.idSanPham.id = :idSanPham or :idSanPham is null) " +
                    "and (spct.idMauSac.id = :idMauSac or :idMauSac is null) " +
                    "and (spct.idKhoiLuong.id = :idKhoiLuong or :idKhoiLuong is null) ")
    Page<SanPhamChiTiet> searchSPCT(@Param("idSanPham") Integer idSanPham,
                                @Param("idMauSac") Integer idMauSac,
                                @Param("idKhoiLuong") Integer idKhoiLuong,
                                Pageable pageable);

    @Query("select spct from SanPhamChiTiet spct where spct.idMauSac.id = :idMauSac " +
            "and spct.idKhoiLuong.id = :idKhoiLuong and spct.idSanPham.id = :idSanPham")
    SanPhamChiTiet getOneByMauSacAndKhoiLuong(@Param("idMauSac") Integer idMauSac,
                                              @Param("idKhoiLuong") Integer idKhoiLuong,
                                              @Param("idSanPham") Integer idSanPham);
}
