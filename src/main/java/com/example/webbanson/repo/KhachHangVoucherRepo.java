package com.example.webbanson.repo;

import com.example.webbanson.model.KhachHang;
import com.example.webbanson.model.KhachhangVoucher;
import com.example.webbanson.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangVoucherRepo extends JpaRepository<KhachhangVoucher, Integer> {

    boolean existsByIdKhachHangAndIdVoucher(KhachHang idKhachHang, Voucher idVoucher);
}
