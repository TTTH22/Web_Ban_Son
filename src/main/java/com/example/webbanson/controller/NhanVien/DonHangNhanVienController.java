package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.*;
import com.example.webbanson.service.HoaDonService;
import com.example.webbanson.service.RankService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nhan-vien/don-hang")
public class DonHangNhanVienController {

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private RankService rankService;

    @PutMapping("/xac-nhan/{id}")
    public ResponseEntity<?> xacNhanDonHang(@PathVariable Integer id, HttpSession session) {
        HoaDon hoaDon = hoaDonService.getOneBanHangOnlineById(id);
        hoaDon.setTrangThai(2);
        hoaDonService.save(hoaDon);
        return ResponseEntity.ok("Đã xác nhận đơn hàng");
    }

    @PostMapping("/hoan-hang/{id}")
    public ResponseEntity<?> xacNhanHoanHang(@PathVariable("id") Integer id) {
        HoaDon hoaDon = hoaDonService.getOneBanHangOnlineById(id);
        hoaDon.setTrangThai(5);
        List<HoaDonChiTiet> listHoaDonChiTiet = hoaDon.getListHoaDonChiTiet();
        for (HoaDonChiTiet hoaDonChiTiet : listHoaDonChiTiet) {
            SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getIdSanPhamChiTiet();
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + hoaDonChiTiet.getSoLuong());
            sanPhamChiTiet.setSoLuongBan(sanPhamChiTiet.getSoLuongBan() - hoaDonChiTiet.getSoLuong());
        }
        KhachHang khachHang = hoaDon.getIdKhachHang();
        khachHang.setTongChiTieu(khachHang.getTongChiTieu() - hoaDon.getTongTien());
        Rank rankBronze = rankService.getRankById(1);
        Rank rankSiliver = rankService.getRankById(2);
        Rank rankGold = rankService.getRankById(3);
        if(khachHang.getTongChiTieu() >= 5000000) {
            khachHang.setIdRank(rankBronze);
        } else if(khachHang.getTongChiTieu() >= 10000000) {
            khachHang.setIdRank(rankSiliver);
        } else {
            khachHang.setIdRank(rankGold);
        }
        hoaDonService.save(hoaDon);
        return ResponseEntity.ok("Đã hoàn trả đơn hàng");
    }

}
