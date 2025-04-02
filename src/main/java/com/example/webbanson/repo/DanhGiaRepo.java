package com.example.webbanson.repo;

import com.example.webbanson.model.DanhGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhGiaRepo extends JpaRepository<DanhGia, Integer> {

    @Query(value = """
    SELECT 1.0 * SUM(d.sao) / COUNT(d.id)
    FROM DanhGia d
    JOIN d.idHoaDon h
    JOIN h.listHoaDonChiTiet hdct
    JOIN hdct.idSanPhamChiTiet spct
    WHERE spct.idSanPham.id = :idSanPham
    """)
    Double getSoSaoBySanPham(@Param("idSanPham") Integer idSanPham);

    @Query(value = """
    SELECT COUNT(d.id)
    FROM DanhGia d
    JOIN d.idHoaDon h
    JOIN h.listHoaDonChiTiet hdct
    JOIN hdct.idSanPhamChiTiet spct
    WHERE spct.idSanPham.id = :idSanPham
    """)
    Integer getSoNguoiDanhGia(@Param("idSanPham") Integer idSanPham);

}
