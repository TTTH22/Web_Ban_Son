package com.example.webbanson.controller.KhachHang;

import com.example.webbanson.model.*;
import com.example.webbanson.service.DanhGiaService;
import com.example.webbanson.service.HoaDonService;
import com.example.webbanson.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/don-hang")
public class DonHangController {

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private DanhGiaService danhGiaService;

    @Autowired
    private RankService rankService;

    @PostMapping("/huy-don/{id}")
    public ResponseEntity<?> huyDon(@PathVariable Integer id, @RequestParam String lyDo) {
        HoaDon hoaDon = hoaDonService.getOneBanHangOnlineById(id);
        hoaDon.setTrangThai(6);
        hoaDon.setLyDoHuy(lyDo);
        hoaDon.setNgayHuyHang(LocalDate.now());
        hoaDonService.save(hoaDon);
        return ResponseEntity.ok("Đã hủy");
    }

    @PostMapping("/da-nhan/{id}")
    public ResponseEntity<?> nhanDon(@PathVariable Integer id) {
        HoaDon hoaDon = hoaDonService.getOneBanHangOnlineById(id);
        hoaDon.setTrangThai(3);
        hoaDon.setNgayNhan(LocalDate.now());
        hoaDon.setDanhGia(false);
        List<HoaDonChiTiet> listHoaDonChiTiet = hoaDon.getListHoaDonChiTiet();
        for (HoaDonChiTiet hoaDonChiTiet : listHoaDonChiTiet) {
            SanPhamChiTiet sanPhamChiTiet = hoaDonChiTiet.getIdSanPhamChiTiet();
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - hoaDonChiTiet.getSoLuong());
            sanPhamChiTiet.setSoLuongBan(sanPhamChiTiet.getSoLuongBan() + hoaDonChiTiet.getSoLuong());
        }
        KhachHang khachHang = hoaDon.getIdKhachHang();
        khachHang.setTongChiTieu(khachHang.getTongChiTieu() + hoaDon.getTongTien());
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
        return ResponseEntity.ok("Đã nhận");
    }

    @PostMapping("/danh-gia")
    public ResponseEntity<?> danhGiaSanPham(@RequestBody Map<String, Object> data) {
        Integer idDonHang = (Integer) data.get("idDonHang");
        Integer soSao = Integer.parseInt(data.get("soSao").toString());
        String noiDung = (String) data.get("noiDung");
        HoaDon hoaDon = hoaDonService.getOneBanHangOnlineById(idDonHang);
        DanhGia danhGia = new DanhGia(null, hoaDon, noiDung, soSao, LocalDate.now(), false);
        danhGiaService.save(danhGia);
        return ResponseEntity.ok("Đã gửi đánh giá");
    }

    @PostMapping("/hoan-hang")
    public ResponseEntity<String> yeuCauHoanHang(@RequestBody Map<String, Object> data) {
        Integer idDonHang = Integer.parseInt(data.get("hoaDonId").toString());
        String lyDo = (String) data.get("lyDo");
        String moTa = (String) data.get("moTa");
        HoaDon hoaDon = hoaDonService.getOneBanHangOnlineById(idDonHang);

        hoaDon.setTrangThai(4);
        hoaDon.setLyDoHoanHang(lyDo);
        hoaDon.setGhiChuHoanHang(moTa);
        hoaDon.setNgayHoanHang(LocalDate.now());
        hoaDonService.save(hoaDon);
        return ResponseEntity.ok("Yêu cầu hoàn hàng đã được ghi nhận");
    }

}
