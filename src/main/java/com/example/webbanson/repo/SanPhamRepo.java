package com.example.webbanson.repo;

import com.example.webbanson.dto.SanPhamThongTinDTO;
import com.example.webbanson.model.SanPham;
import com.example.webbanson.model.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepo extends JpaRepository<SanPham, Integer> {

    @Query("SELECT sp FROM SanPham sp ORDER BY sp.id DESC")
    List<SanPham> findSanPhamByIdDesc();


    //Sản phẩm bán chạy nhất
    @Query("SELECT DISTINCT sp, MIN(spct.donGiaGiam), SUM(spct.soLuongBan) " +
            "FROM SanPham sp " +
            "JOIN sp.sanPhamChiTietList spct " +
            "GROUP BY sp order by SUM(spct.soLuongBan) desc")
    List<Object[]> listSanPhamBanChay(Pageable pageable);

    //List Sản phẩm
    @Query("SELECT DISTINCT sp, MIN(spct.donGiaGiam), SUM(spct.soLuongBan) " +
            "FROM SanPham sp " +
            "JOIN sp.sanPhamChiTietList spct " +
            "GROUP BY sp")
    List<Object[]> listSanPham(Pageable pageable);

    //Sản phẩm giảm giá nhiều nhất
    @Query(value = "SELECT DISTINCT sp.id, MAX(100 - spct.donGiaGiam / spct.donGia * 100) AS max_giam, SUM(spct.soLuongBan) " +
            "FROM SanPham sp " +
            "JOIN SanPhamChiTiet spct ON sp.id = spct.idSanPham " +
            "GROUP BY sp.id, sp.ten " +
            "ORDER BY max_giam DESC",
            nativeQuery = true)
    List<Object[]> listSanPhamGiamGia(Pageable pageable);

    Page<SanPham> findAll(Pageable pageable);

    @Query("SELECT sp FROM SanPham sp ORDER BY sp.id DESC")
    Page<SanPham> findAllByIdDesc(Pageable pageable);

    //Đếm số lượng sản phẩm
    @Query("SELECT count(sp) FROM SanPham sp")
    long countSanPham();

    //Tìm sản phẩm theo fillter
    @Query(value = "SELECT DISTINCT sp, MIN(spct.donGiaGiam), SUM(spct.soLuongBan) " +
            "FROM SanPham sp " +
            "JOIN sp.sanPhamChiTietList spct " +
            "WHERE (:idMauSac IS NULL OR spct.idMauSac.id IN :idMauSac) " +
            "AND (:idNsx IS NULL OR sp.idNSX.id IN :idNsx) " +
            "AND (:idDongSanPham IS NULL OR sp.idDongSanPham.id = :idDongSanPham) " +
            "AND (:tenSearch IS NULL OR sp.ten LIKE %:tenSearch%) " +
            "GROUP BY sp " +
            "HAVING MIN(spct.donGiaGiam) BETWEEN :minPrice AND :maxPrice",
            countQuery = "SELECT count(DISTINCT sp.id) " +
            "FROM SanPham sp " +
            "JOIN sp.sanPhamChiTietList spct " +
            "WHERE (:idMauSac IS NULL OR spct.idMauSac.id IN :idMauSac) " +
            "AND (:idNsx IS NULL OR sp.idNSX.id IN :idNsx) " +
            "AND (:idDongSanPham IS NULL OR sp.idDongSanPham.id = :idDongSanPham) " +
            "AND (:tenSearch IS NULL OR sp.ten LIKE %:tenSearch%) " +
            "GROUP BY sp " +
            "HAVING MIN(spct.donGiaGiam) BETWEEN :minPrice AND :maxPrice")
    Page<Object[]> searchSanPhamCategory1(@Param("minPrice") Float minPrice,
                                          @Param("maxPrice") Float maxPrice,
                                          @Param("idMauSac") List<Integer> idMauSac,
                                          @Param("idNsx") List<Integer> idNsx,
                                          @Param("idDongSanPham") Integer idDongSanPham,
                                          @Param("tenSearch") String tenSearch,
                                          @Param("category") Integer category,
                                          Pageable pageable);

    @Query(value = "SELECT DISTINCT sp, MIN(spct.donGiaGiam), SUM(spct.soLuongBan) " +
            "FROM SanPham sp " +
            "JOIN sp.sanPhamChiTietList spct " +
            "WHERE (:idMauSac IS NULL OR spct.idMauSac.id IN :idMauSac) " +
            "AND (:idNsx IS NULL OR sp.idNSX.id IN :idNsx) " +
            "AND (:idDongSanPham IS NULL OR sp.idDongSanPham.id = :idDongSanPham) " +
            "AND (:tenSearch IS NULL OR sp.ten LIKE %:tenSearch%) " +
            "group by sp having MIN(spct.donGiaGiam) between :minPrice and :maxPrice  order by SUM(spct.soLuongBan) desc ",
            countQuery = "SELECT count(distinct sp.id) " +
            "FROM SanPham sp " +
            "JOIN sp.sanPhamChiTietList spct " +
            "WHERE (:idMauSac IS NULL OR spct.idMauSac.id IN :idMauSac) " +
            "AND (:idNsx IS NULL OR sp.idNSX.id IN :idNsx) " +
            "AND (:idDongSanPham IS NULL OR sp.idDongSanPham.id = :idDongSanPham) " +
            "AND (:tenSearch IS NULL OR sp.ten LIKE %:tenSearch%) " +
            "group by sp having MIN(spct.donGiaGiam) between :minPrice and :maxPrice  order by SUM(spct.soLuongBan) desc ")
    Page<Object[]> searchSanPhamCategory2(@Param("minPrice") Float minPrice,
                                          @Param("maxPrice") Float maxPrice,
                                          @Param("idMauSac") List<Integer> idMauSac,
                                          @Param("idNsx") List<Integer> idNsx,
                                          @Param("idDongSanPham") Integer idDongSanPham,
                                          @Param("tenSearch") String tenSearch,
                                          @Param("category") Integer category,
                                          Pageable pageable);


    @Query(value = "SELECT sp, MIN(spct.donGiaGiam), SUM(spct.soLuongBan) " +
            "FROM SanPham sp " +
            "JOIN sp.sanPhamChiTietList spct " +
            "WHERE (:idMauSac IS NULL OR spct.idMauSac.id IN :idMauSac) " +
            "AND (:idNsx IS NULL OR sp.idNSX.id IN :idNsx) " +
            "AND (:idDongSanPham IS NULL OR sp.idDongSanPham.id = :idDongSanPham) " +
            "AND (:tenSearch IS NULL OR sp.ten LIKE %:tenSearch%) " +
            "GROUP BY sp having MIN(spct.donGiaGiam) BETWEEN :minPrice AND :maxPrice " +
            "ORDER BY MAX(spct.ngayMoBan) DESC",
            countQuery = "SELECT count(distinct sp.id) " +
            "FROM SanPham sp " +
            "JOIN sp.sanPhamChiTietList spct " +
            "WHERE (:idMauSac IS NULL OR spct.idMauSac.id IN :idMauSac) " +
            "AND (:idNsx IS NULL OR sp.idNSX.id IN :idNsx) " +
            "AND (:idDongSanPham IS NULL OR sp.idDongSanPham.id = :idDongSanPham) " +
            "AND (:tenSearch IS NULL OR sp.ten LIKE %:tenSearch%) " +
            "GROUP BY sp having MIN(spct.donGiaGiam) BETWEEN :minPrice AND :maxPrice " +
            "ORDER BY MAX(spct.ngayMoBan) DESC")
    Page<Object[]> searchSanPhamCategory3(@Param("minPrice") Float minPrice,
                                          @Param("maxPrice") Float maxPrice,
                                          @Param("idMauSac") List<Integer> idMauSac,
                                          @Param("idNsx") List<Integer> idNsx,
                                          @Param("idDongSanPham") Integer idDongSanPham,
                                          @Param("tenSearch") String tenSearch,
                                          @Param("category") Integer category,
                                          Pageable pageable);

    @Query(value = "SELECT DISTINCT sp, MIN(spct.donGiaGiam), SUM(spct.soLuongBan) " +
            "FROM SanPham sp " +
            "JOIN sp.sanPhamChiTietList spct " +
            "WHERE (:idMauSac IS NULL OR spct.idMauSac.id IN :idMauSac) " +
            "AND (:idNsx IS NULL OR sp.idNSX.id IN :idNsx) " +
            "AND (:idDongSanPham IS NULL OR sp.idDongSanPham.id = :idDongSanPham) " +
            "AND (:tenSearch IS NULL OR sp.ten LIKE %:tenSearch%) " +
            "group by sp having MIN(spct.donGiaGiam) between :minPrice and :maxPrice  order by MIN(spct.donGiaGiam) desc ",
            countQuery = "SELECT count(distinct sp.id) " +
                    "FROM SanPham sp " +
                    "JOIN sp.sanPhamChiTietList spct " +
                    "WHERE (:idMauSac IS NULL OR spct.idMauSac.id IN :idMauSac) " +
                    "AND (:idNsx IS NULL OR sp.idNSX.id IN :idNsx) " +
                    "AND (:idDongSanPham IS NULL OR sp.idDongSanPham.id = :idDongSanPham) " +
                    "AND (:tenSearch IS NULL OR sp.ten LIKE %:tenSearch%) " +
                    "group by sp having MIN(spct.donGiaGiam) between :minPrice and :maxPrice  order by MIN(spct.donGiaGiam) desc ")
    Page<Object[]> searchSanPhamCategory4(@Param("minPrice") Float minPrice,
                                          @Param("maxPrice") Float maxPrice,
                                          @Param("idMauSac") List<Integer> idMauSac,
                                          @Param("idNsx") List<Integer> idNsx,
                                          @Param("idDongSanPham") Integer idDongSanPham,
                                          @Param("tenSearch") String tenSearch,
                                          @Param("category") Integer category,
                                          Pageable pageable);


    @Query(value = "SELECT DISTINCT sp, MIN(spct.donGiaGiam), SUM(spct.soLuongBan) " +
            "FROM SanPham sp " +
            "JOIN sp.sanPhamChiTietList spct " +
            "WHERE (:idMauSac IS NULL OR spct.idMauSac.id IN :idMauSac) " +
            "AND (:idNsx IS NULL OR sp.idNSX.id IN :idNsx) " +
            "AND (:idDongSanPham IS NULL OR sp.idDongSanPham.id = :idDongSanPham) " +
            "AND (:tenSearch IS NULL OR sp.ten LIKE %:tenSearch%) " +
            "group by sp having MIN(spct.donGiaGiam) between :minPrice and :maxPrice  order by MIN(spct.donGiaGiam) asc ",
            countQuery = "SELECT count(DISTINCT sp.id) " +
            "FROM SanPham sp " +
            "JOIN sp.sanPhamChiTietList spct " +
            "WHERE (:idMauSac IS NULL OR spct.idMauSac.id IN :idMauSac) " +
            "AND (:idNsx IS NULL OR sp.idNSX.id IN :idNsx) " +
            "AND (:idDongSanPham IS NULL OR sp.idDongSanPham.id = :idDongSanPham) " +
            "AND (:tenSearch IS NULL OR sp.ten LIKE %:tenSearch%) " +
            "group by sp having MIN(spct.donGiaGiam) between :minPrice and :maxPrice  order by MIN(spct.donGiaGiam) asc ")
    Page<Object[]> searchSanPhamCategory5(@Param("minPrice") Float minPrice,
                                          @Param("maxPrice") Float maxPrice,
                                          @Param("idMauSac") List<Integer> idMauSac,
                                          @Param("idNsx") List<Integer> idNsx,
                                          @Param("idDongSanPham") Integer idDongSanPham,
                                          @Param("tenSearch") String tenSearch,
                                          @Param("category") Integer category,
                                          Pageable pageable);


    @Query(value="select sp from SanPham sp where" +
            " sp.ten like %:tenSearch% " +
            "and (sp.idNSX.id = :idNSX or :idNSX is null) " +
            "and (sp.idDongSanPham.id = :idDongSanPham or :idDongSanPham is null) " ,
             countQuery = "select count(sp) from SanPham sp where" +
                    " sp.ten like %:tenSearch% " +
                    "and (sp.idNSX = :idNSX or :idNSX is null) " +
                    "and (sp.idDongSanPham = :idDongSanPham or :idDongSanPham is null) ")
    Page<SanPham> searchSanPham(@Param("tenSearch") String tenSearch,
                                @Param("idNSX") Integer idNSX,
                                @Param("idDongSanPham") Integer idDongSanPham,
                                Pageable pageable);


    @Query("SELECT sp FROM SanPham sp WHERE sp.trangThai = true")
    List<SanPham> getAllConSuDung();
}
