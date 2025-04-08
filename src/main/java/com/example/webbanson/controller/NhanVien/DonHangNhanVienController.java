package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.HoaDon;
import com.example.webbanson.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nhan-vien/don-hang")
public class DonHangNhanVienController {

    @Autowired
    private HoaDonService hoaDonService;

    @PutMapping("/xac-nhan/{id}")
    public ResponseEntity<?> xacNhanDonHang(@PathVariable Integer id) {
        HoaDon hoaDon = hoaDonService.getOneBanHangOnlineById(id);
        hoaDon.setTrangThai(2);
        hoaDonService.save(hoaDon);
        return ResponseEntity.ok("Đã xác nhận đơn hàng");
    }

    @PostMapping("/hoan-hang/{id}")
    public ResponseEntity<?> xacNhanHoanHang(@PathVariable("id") Integer id) {
        HoaDon hoaDon = hoaDonService.getOneBanHangOnlineById(id);
        hoaDon.setTrangThai(5);
        hoaDonService.save(hoaDon);
        return ResponseEntity.ok("Đã hoàn trả đơn hàng");
    }

}
