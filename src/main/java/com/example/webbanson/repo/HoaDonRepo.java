package com.example.webbanson.repo;

import com.example.webbanson.dto.DoanhThuTheoDanhMuc;
import com.example.webbanson.dto.DoanhThuTheoKenh;
import com.example.webbanson.dto.KhachHangMuaNhieuNhat;
import com.example.webbanson.dto.SanPhamBanChayDto;
import com.example.webbanson.model.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDon, Integer> {

    Page<HoaDon> findAll(Pageable pageable);

    Page<HoaDon> findAllByHinhThuc(Boolean hinhThuc, Pageable pageable);

    @Query("SELECT count(h.id) FROM HoaDon h ")
    Integer countAll();

    @Query("SELECT count(h.id) FROM HoaDon h where h.hinhThuc = true")
    Integer countAllDonOnline();

    @Query("SELECT count(h.id) FROM HoaDon h where h.hinhThuc = false ")
    Integer countAllDonOfline();

    @Query("SELECT sum(h.tongTien) FROM HoaDon h")
    Double countDoanhThu();


    @Query(value = "SELECT h FROM HoaDon h WHERE h.hinhThuc = false ",
    countQuery = "SELECT count(h) FROM HoaDon h WHERE h.hinhThuc = false ")
    Page<HoaDon> getAllHoaDonOfline(Pageable pageable);

    @Query("SELECT h FROM HoaDon h WHERE h.id = :id")
    HoaDon getOneBanHangOnlineById(@Param("id") Integer id);

    @Query("SELECT h FROM HoaDon h WHERE h.hinhThuc = true and h.idKhachHang.id = :idKhachHang and h.trangThai = 1 order by h.id desc")
    List<HoaDon> getHoaDonChoXacNhan(@Param("idKhachHang") Integer idKhachHang);

    @Query("SELECT h FROM HoaDon h WHERE h.hinhThuc = true and h.idKhachHang.id = ?1 and h.trangThai = 2 order by h.id desc")
    List<HoaDon> listHoaDonDangGiao(Integer idKhachHang);

    @Query("SELECT h FROM HoaDon h WHERE h.hinhThuc = true and h.idKhachHang.id = ?1 and h.trangThai = 3 order by h.id desc")
    List<HoaDon> listHoaDonDaGiao(Integer idKhachHang);

    @Query("SELECT h FROM HoaDon h WHERE h.hinhThuc = true and h.idKhachHang.id = ?1 and h.trangThai = 4 order by h.id desc")
    List<HoaDon> listHoaDonChoHoan(Integer idKhachHang);

    @Query("SELECT h FROM HoaDon h WHERE h.hinhThuc = true and h.idKhachHang.id = ?1 and h.trangThai = 5 order by h.id desc")
    List<HoaDon> listHoaDonDaHoan(Integer idKhachHang);

    @Query("SELECT h FROM HoaDon h WHERE h.hinhThuc = true and h.idKhachHang.id = ?1 and h.trangThai = 6  order by h.id desc")
    List<HoaDon> listHoaDonDaHuy(Integer idKhachHang);

    @Query("SELECT h FROM HoaDon h WHERE h.hinhThuc = true and h.trangThai = 1 order by h.id desc")
    List<HoaDon> getAllHoaDonChoXacNhan();

    @Query("SELECT h FROM HoaDon h WHERE h.hinhThuc = true and h.trangThai = 2 order by h.id desc")
    List<HoaDon> listAllHoaDonDangGiao();

    @Query("SELECT h FROM HoaDon h WHERE h.hinhThuc = true and h.trangThai = 3 order by h.id desc")
    List<HoaDon> listAllHoaDonDaGiao();

    @Query("SELECT h FROM HoaDon h WHERE h.hinhThuc = true and h.trangThai = 4 order by h.id desc")
    List<HoaDon> listAllHoaDonChoHoan();

    @Query("SELECT h FROM HoaDon h WHERE h.hinhThuc = true and h.trangThai = 5 order by h.id desc")
    List<HoaDon> listAllHoaDonDaHoan();

    @Query("SELECT h FROM HoaDon h WHERE h.hinhThuc = true and h.trangThai = 6  order by h.id desc")
    List<HoaDon> listAllHoaDonDaHuy();

    @Query("SELECT SUM(h.tongTien) FROM HoaDon h WHERE FUNCTION('MONTH', h.ngayTao) = :month and h.trangThai = 3")
    Double doanhThuThang(@Param("month") int month);

    @Query("SELECT COUNT(h) FROM HoaDon h WHERE FUNCTION('MONTH', h.ngayTao) = :month and h.trangThai = 3")
    Integer donHangThang(@Param("month") int currentMonth);

    @Query("SELECT COUNT(h) FROM HoaDon h WHERE FUNCTION('MONTH', h.ngayTao) = :month and h.trangThai = 6")
    Integer donHangHuyThang(@Param("month") int currentMonth);

    @Query("SELECT COUNT(h) FROM HoaDon h WHERE FUNCTION('MONTH', h.ngayTao) = :month and h.trangThai = 5")
    Integer donHangHoanThang(@Param("month") int currentMonth);

    @Query("SELECT COUNT(h) FROM HoaDon h WHERE FUNCTION('MONTH', h.ngayTao) = :month and (h.trangThai = 6 or h.trangThai = 3 or h.trangThai = 5)")
    Integer donHangAllThang(@Param("month") int currentMonth);

    @Query("SELECT SUM(h.soLuong) FROM HoaDonChiTiet h WHERE FUNCTION('MONTH', h.idHoaDon.ngayTao) = :month and h.idHoaDon.trangThai = 3")
    Integer soSanPhamThang(@Param("month") int month);

    @Query("SELECT new com.example.webbanson.dto.SanPhamBanChayDto(" +
            "h.idSanPhamChiTiet.idSanPham.ten, " +
            "SUM(CAST(h.soLuong AS double) * CAST(h.donGia AS double))) " +
            "FROM HoaDonChiTiet h " +
            "WHERE FUNCTION('MONTH', h.idHoaDon.ngayTao) = :month AND h.idHoaDon.trangThai = 3 " +
            "GROUP BY h.idSanPhamChiTiet.idSanPham.ten " +
            "ORDER BY SUM(CAST(h.soLuong AS double) * CAST(h.donGia AS double)) DESC")
    List<SanPhamBanChayDto> topSanPhamBanChay(@Param("month") int month,  Pageable pageable);

    @Query("SELECT new com.example.webbanson.dto.DoanhThuTheoDanhMuc(" +
            "h.idSanPhamChiTiet.idSanPham.idDongSanPham.ten, " +
            "SUM(CAST(h.soLuong AS double) * CAST(h.donGia AS double))) " +
            "FROM HoaDonChiTiet h " +
            "WHERE FUNCTION('MONTH', h.idHoaDon.ngayTao) = :month AND h.idHoaDon.trangThai = 3 " +
            "GROUP BY h.idSanPhamChiTiet.idSanPham.idDongSanPham.ten " +
            "ORDER BY SUM(CAST(h.soLuong AS double) * CAST(h.donGia AS double)) DESC")
    List<DoanhThuTheoDanhMuc> DoanhThuTheoDanhMuc(@Param("month") int month);

    @Query("SELECT new com.example.webbanson.dto.DoanhThuTheoKenh(" +
            "CASE WHEN h.hinhThuc = false THEN '0' ELSE '1' END, " +
            "SUM(h.tongTien)) " +
            "FROM HoaDon h " +
            "WHERE FUNCTION('MONTH', h.ngayTao) = :month AND h.trangThai = 3 " +
            "GROUP BY h.hinhThuc")
    List<DoanhThuTheoKenh> DoanhThuTheoKenh(@Param("month") int month);

    @Query("SELECT new com.example.webbanson.dto.KhachHangMuaNhieuNhat" +
            "(h.idKhachHang.ten, count(h.id), sum(h.tongTien), 0) " +
            "FROM HoaDon h " +
            "WHERE FUNCTION('MONTH', h.ngayTao) = :month AND h.trangThai = 3 " +
            "GROUP BY h.idKhachHang.ten order by sum(h.tongTien) desc")
    List<KhachHangMuaNhieuNhat> KhachHangMuaNhieuNhat(@Param("month") int month);

    @Query(value = "SELECT COUNT(DISTINCT h.idKhachHang) FROM HoaDon h " +
            "WHERE MONTH(h.ngayTao) = :month AND h.trangThai = 3 " +
            "AND (h.ngayTao - h.idKhachHang.ngayTao) <= 30")
    Integer countKhachHangMoi(@Param("month") int month);

    @Query(value = "SELECT COUNT(DISTINCT h.idKhachHang) FROM HoaDon h " +
            "WHERE MONTH(h.ngayTao) = :month AND h.trangThai = 3 " +
            "AND (h.ngayTao - h.idKhachHang.ngayTao) > 30")
    Integer countKhachHangCu(@Param("month") int month);

    // Repository mở rộng với tham số ngày bắt đầu và kết thúc ------------------------------------------------

    @Query("SELECT SUM(h.tongTien) FROM HoaDon h WHERE h.ngayTao BETWEEN :startDate AND :endDate AND h.trangThai = 3")
    Double doanhThuKhoangNgay(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(h) FROM HoaDon h WHERE h.ngayTao BETWEEN :startDate AND :endDate AND h.trangThai = 3")
    Integer donHangThanhCongKhoangNgay(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(h) FROM HoaDon h WHERE h.ngayTao BETWEEN :startDate AND :endDate AND h.trangThai = 6")
    Integer donHangHuyKhoangNgay(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(h) FROM HoaDon h WHERE h.ngayTao BETWEEN :startDate AND :endDate AND h.trangThai = 5")
    Integer donHangHoanKhoangNgay(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(h) FROM HoaDon h WHERE h.ngayTao BETWEEN :startDate AND :endDate AND (h.trangThai = 3 OR h.trangThai = 5 OR h.trangThai = 6)")
    Integer donHangAllKhoangNgay(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(h.soLuong) FROM HoaDonChiTiet h WHERE h.idHoaDon.ngayTao BETWEEN :startDate AND :endDate AND h.idHoaDon.trangThai = 3")
    Integer soSanPhamKhoangNgay(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT new com.example.webbanson.dto.SanPhamBanChayDto(" +
            "h.idSanPhamChiTiet.idSanPham.ten, " +
            "SUM(CAST(h.soLuong AS double) * CAST(h.donGia AS double))) " +
            "FROM HoaDonChiTiet h " +
            "WHERE h.idHoaDon.ngayTao BETWEEN :startDate AND :endDate AND h.idHoaDon.trangThai = 3 " +
            "GROUP BY h.idSanPhamChiTiet.idSanPham.ten " +
            "ORDER BY SUM(CAST(h.soLuong AS double) * CAST(h.donGia AS double)) DESC")
    List<SanPhamBanChayDto> topSanPhamBanChayKhoangNgay(@Param("startDate") LocalDate startDate,
                                                             @Param("endDate") LocalDate endDate,
                                                             Pageable pageable);

    @Query("SELECT new com.example.webbanson.dto.DoanhThuTheoDanhMuc(" +
            "h.idSanPhamChiTiet.idSanPham.idDongSanPham.ten, " +
            "SUM(CAST(h.soLuong AS double) * CAST(h.donGia AS double))) " +
            "FROM HoaDonChiTiet h " +
            "WHERE h.idHoaDon.ngayTao BETWEEN :startDate AND :endDate AND h.idHoaDon.trangThai = 3 " +
            "GROUP BY h.idSanPhamChiTiet.idSanPham.idDongSanPham.ten " +
            "ORDER BY SUM(CAST(h.soLuong AS double) * CAST(h.donGia AS double)) DESC")
    List<DoanhThuTheoDanhMuc> DoanhThuTheoDanhMucKhoangNgay(@Param("startDate") LocalDate startDate,
                                                                 @Param("endDate") LocalDate endDate);

    @Query("SELECT new com.example.webbanson.dto.DoanhThuTheoKenh(" +
            "CASE WHEN h.hinhThuc = false THEN '0' ELSE '1' END, " +
            "SUM(h.tongTien)) " +
            "FROM HoaDon h " +
            "WHERE h.ngayTao BETWEEN :startDate AND :endDate AND h.trangThai = 3 " +
            "GROUP BY h.hinhThuc")
    List<DoanhThuTheoKenh> DoanhThuTheoKenhKhoangNgay(@Param("startDate") LocalDate startDate,
                                                           @Param("endDate") LocalDate endDate);

    @Query("SELECT new com.example.webbanson.dto.KhachHangMuaNhieuNhat" +
            "(h.idKhachHang.ten, COUNT(h.id), SUM(h.tongTien), 0) " +
            "FROM HoaDon h " +
            "WHERE h.ngayTao BETWEEN :startDate AND :endDate AND h.trangThai = 3 " +
            "GROUP BY h.idKhachHang.ten " +
            "ORDER BY SUM(h.tongTien) DESC")
    List<KhachHangMuaNhieuNhat> KhachHangMuaNhieuNhatKhoangNgay(@Param("startDate") LocalDate startDate,
                                                                     @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(DISTINCT h.idKhachHang) FROM HoaDon h " +
            "WHERE h.ngayTao BETWEEN :startDate AND :endDate AND h.trangThai = 3 " +
            "AND (h.ngayTao - h.idKhachHang.ngayTao) <= 30")
    Integer countKhachHangMoiKhoangNgay(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(DISTINCT h.idKhachHang) FROM HoaDon h " +
            "WHERE h.ngayTao BETWEEN :startDate AND :endDate AND h.trangThai = 3 " +
            "AND (h.ngayTao - h.idKhachHang.ngayTao) > 30")
    Integer countKhachHangCuKhoangNgay(@Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);


}
