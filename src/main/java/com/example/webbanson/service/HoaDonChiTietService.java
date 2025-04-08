package com.example.webbanson.service;

import com.example.webbanson.model.HoaDonChiTiet;
import com.example.webbanson.repo.HoaDonChiTietRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HoaDonChiTietService {
    private final HoaDonChiTietRepo repo;

    public void save(HoaDonChiTiet hoaDonChiTiet) {
        repo.save(hoaDonChiTiet);
    }
}
