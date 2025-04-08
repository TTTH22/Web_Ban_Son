package com.example.webbanson.repo;

import com.example.webbanson.model.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepo extends JpaRepository<Voucher, Integer> {

    @Query("select v.idVoucher from KhachhangVoucher v where v.idVoucher.hinhThucGiam = true " +
            "and v.idVoucher.trangThai = true and v.idKhachHang.id = ?1")
    List<Voucher> listVocherSanPham(Integer idKhachHang);

    @Query("select v.idVoucher from KhachhangVoucher v where v.idVoucher.hinhThucGiam = false " +
            "and v.idVoucher.trangThai = true and v.idKhachHang.id = ?1")
    List<Voucher> listVocherVanChuyen(Integer idKhachHang);

    Page<Voucher> findAll(Pageable pageable);

    @Query("select v from Voucher v order by v.id desc")
    Page<Voucher> findAllIdDesc(Pageable pageable);

    @Query(value="select v from Voucher v where (:tenSearch is null or v.ten like %:tenSearch%) and " +
            "(:hinhThucSearch = 1 or " +
            "(:hinhThucSearch = 2 and v.hinhThucGiam = true) or " +
            "(:hinhThucSearch = 3 and v.hinhThucGiam = false)) and " +
            "(:loaiGiamSearch = 1 or " +
            "(:loaiGiamSearch = 2 and v.loaiGiam = true) or " +
            "(:loaiGiamSearch = 3 and v.loaiGiam = false)) and " +
            "(:trangThaiSearch = 1 or " +
            "(:trangThaiSearch = 2 and v.trangThai = true) or " +
            "(:trangThaiSearch = 3 and v.trangThai = false))" ,
        countQuery = "SELECT COUNT(v) FROM Voucher v WHERE (:tenSearch is null or v.ten like %:tenSearch%) and " +
                "(:hinhThucSearch = 1 or " +
                "(:hinhThucSearch = 2 and v.hinhThucGiam = true) or " +
                "(:hinhThucSearch = 3 and v.hinhThucGiam = false)) and " +
                "(:loaiGiamSearch = 1 or " +
                "(:loaiGiamSearch = 2 and v.loaiGiam = true) or " +
                "(:loaiGiamSearch = 3 and v.loaiGiam = false)) and " +
                "(:trangThaiSearch = 1 or " +
                "(:trangThaiSearch = 2 and v.trangThai = true) or " +
                "(:trangThaiSearch = 3 and v.trangThai = false))")
    Page<Voucher> searchVoucher(String tenSearch, Integer hinhThucSearch, Integer loaiGiamSearch, Integer trangThaiSearch, Pageable pageable);
}
