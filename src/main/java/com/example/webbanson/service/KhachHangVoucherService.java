package com.example.webbanson.service;

import com.example.webbanson.model.KhachHang;
import com.example.webbanson.model.KhachhangVoucher;
import com.example.webbanson.model.Voucher;
import com.example.webbanson.repo.KhachHangVoucherRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KhachHangVoucherService {
    private final KhachHangVoucherRepo repo;

    public List<KhachhangVoucher> getAll() {
        return repo.findAll();
    }

    public void save(KhachhangVoucher khachhangVoucher) {
        repo.save(khachhangVoucher);
    }

    public boolean existsByKhachHangIdAndVoucherId(KhachHang customerId, Voucher voucherId) {
        return repo.existsByIdKhachHangAndIdVoucher(customerId, voucherId);
    }
}
