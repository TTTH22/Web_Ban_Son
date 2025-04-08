package com.example.webbanson.service;

import com.example.webbanson.model.GioHang;
import com.example.webbanson.model.KhachHang;
import com.example.webbanson.repo.GioHangRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GioHangService {
    private final GioHangRepo repo;

    public GioHang getGioHangByIdKhachHang(KhachHang KhachHang) {
        return repo.findByIdKhachHang(KhachHang);
    }
}
