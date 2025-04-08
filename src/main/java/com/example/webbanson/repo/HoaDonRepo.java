package com.example.webbanson.repo;

import com.example.webbanson.model.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDon, Integer> {

    @Query(value = "SELECT h FROM HoaDon h WHERE h.hinhThuc = true",
    countQuery = "SELECT count(h) FROM HoaDon h WHERE h.hinhThuc = true")
    Page<HoaDon> getAllHoaDonOnline(Pageable pageable);

    @Query("SELECT h FROM HoaDon h WHERE h.hinhThuc = true and h.id = :id")
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
}
