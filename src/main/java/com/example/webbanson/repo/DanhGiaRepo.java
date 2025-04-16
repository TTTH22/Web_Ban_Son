package com.example.webbanson.repo;

import com.example.webbanson.model.DanhGia;
import com.example.webbanson.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhGiaRepo extends JpaRepository<DanhGia, Integer> {

    @Query(value = """
    SELECT 1.0 * SUM(d.sao) / COUNT(d.id)
    FROM DanhGia d
    JOIN d.idHoaDon h
    JOIN h.listHoaDonChiTiet hdct
    JOIN hdct.idSanPhamChiTiet spct
    WHERE spct.idSanPham.id = :idSanPham and d.trangThai = true 
    """)
    Double getSoSaoBySanPham(@Param("idSanPham") Integer idSanPham);

    Long countByTrangThai(Boolean trangThai);

    List<DanhGia> findAllByTrangThai(Boolean trangThai);

    @Query("""
    SELECT DISTINCT spct.idSanPham
    FROM DanhGia dg 
    JOIN HoaDon d ON dg.idHoaDon.id = d.id
    JOIN HoaDonChiTiet ct ON d.id = ct.idHoaDon.id
    JOIN SanPhamChiTiet spct ON ct.idSanPhamChiTiet.id = spct.id
    WHERE dg.trangThai = true
""")
    List<SanPham> listSanPhamDaDanhGiaAll();

    @Query("""
    SELECT DISTINCT dg
    FROM DanhGia dg 
    JOIN HoaDon d ON dg.idHoaDon.id = d.id
    JOIN HoaDonChiTiet ct ON d.id = ct.idHoaDon.id
    JOIN SanPhamChiTiet spct ON ct.idSanPhamChiTiet.id = spct.id
    WHERE dg.trangThai = true and spct.idSanPham.id = :productId and dg.sao = :rating
""")

    List<DanhGia> findBySanPhamIdAndSoSao(Integer productId, Integer rating);

    @Query("""
    SELECT DISTINCT dg
    FROM DanhGia dg 
    JOIN HoaDon d ON dg.idHoaDon.id = d.id
    JOIN HoaDonChiTiet ct ON d.id = ct.idHoaDon.id
    JOIN SanPhamChiTiet spct ON ct.idSanPhamChiTiet.id = spct.id
    WHERE dg.trangThai = true and spct.idSanPham.id = :productId
""")
    List<DanhGia> findBySanPhamId(Integer productId);

    @Query("""
                SELECT DISTINCT dg
                FROM DanhGia dg 
                JOIN HoaDon d ON dg.idHoaDon.id = d.id
                JOIN HoaDonChiTiet ct ON d.id = ct.idHoaDon.id
                JOIN SanPhamChiTiet spct ON ct.idSanPhamChiTiet.id = spct.id
                WHERE dg.trangThai = true and dg.sao = :rating
            """)
    List<DanhGia> findBySoSao(Integer rating);
}
