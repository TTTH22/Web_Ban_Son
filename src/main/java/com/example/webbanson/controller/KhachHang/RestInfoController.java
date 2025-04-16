package com.example.webbanson.controller.KhachHang;

import com.example.webbanson.model.DiaChi;
import com.example.webbanson.model.KhachHang;
import com.example.webbanson.service.DiaChiService;
import com.example.webbanson.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RestInfoController {

    @Autowired
    private DiaChiService diaChiService;

    @Autowired
    private KhachHangService khachHangService;

    // Thêm mới
    @PostMapping("/api/dia-chi/add")
    public ResponseEntity<?> themDiaChi(
            @RequestParam Integer idKhachHang,
            @RequestParam String tenNguoiNhan,
            @RequestParam String sdt,
            @RequestParam String diaChiCuThe,
            @RequestParam String xa,
            @RequestParam String huyen,
            @RequestParam String thanhPho
    ) {
        KhachHang khachHang = khachHangService.getOneKhachHangById(idKhachHang);
        DiaChi diaChi = new DiaChi();
        diaChi.setIdKhachHang(khachHang);
        diaChi.setTenNguoiNhan(tenNguoiNhan);
        diaChi.setSdt(sdt);
        diaChi.setDiaChiCuThe(diaChiCuThe);
        diaChi.setXa(xa);
        diaChi.setHuyen(huyen);
        diaChi.setThanhPho(thanhPho);
        diaChi.setTrangThai(false);
        diaChiService.save(diaChi);
        return ResponseEntity.ok(Map.of("success", true));
    }


    // Cập nhật
    @PostMapping("/api/dia-chi/sua")
    public ResponseEntity<?> suaDiaChi(
            @RequestParam Integer idKhachHang,
            @RequestParam String tenNguoiNhan,
            @RequestParam String sdt,
            @RequestParam String diaChiCuThe,
            @RequestParam String xa,
            @RequestParam String huyen,
            @RequestParam String thanhPho,
            @RequestParam Integer id
    ) {
        KhachHang khachHang = khachHangService.getOneKhachHangById(idKhachHang);
        DiaChi diaChi = diaChiService.getOne(id);
        diaChi.setIdKhachHang(khachHang);
        diaChi.setTenNguoiNhan(tenNguoiNhan);
        diaChi.setSdt(sdt);
        diaChi.setDiaChiCuThe(diaChiCuThe);
        diaChi.setXa(xa);
        diaChi.setHuyen(huyen);
        diaChi.setThanhPho(thanhPho);
        diaChi.setTrangThai(false);
        diaChiService.save(diaChi);
        return ResponseEntity.ok(Map.of("success", true));
    }

    // Xóa
    @DeleteMapping("/api/dia-chi/{id}")
    public ResponseEntity<?> xoaDiaChi(@PathVariable Integer id) {
        diaChiService.delete(id);
        return ResponseEntity.ok(Map.of("success", true));
    }

    // Đặt mặc định
    @PutMapping("/api/dia-chi/mac-dinh/{id}")
    public ResponseEntity<?> datMacDinh(@PathVariable Integer id) {
        diaChiService.boTrangThaiTatCa();
        diaChiService.capNhatTrangThai(id, true);
        return ResponseEntity.ok().build();
    }

}
