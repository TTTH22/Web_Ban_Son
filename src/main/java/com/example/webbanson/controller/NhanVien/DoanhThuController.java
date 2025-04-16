package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.dto.KhachHangMuaNhieuNhat;
import com.example.webbanson.model.NhanVien;
import com.example.webbanson.service.HoaDonService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/nhan-vien/doanh-thu")
public class DoanhThuController {

    @Autowired
    HoaDonService hoaDonService;

    @GetMapping("")
    public String DoanhThu(Model model,
                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        if (nhanVien != null) {
            model.addAttribute("nhanVien", nhanVien);
            if (nhanVien.getChucVu().equals("Quản lý")) {
                model.addAttribute("checkLogin", true);
            } else {
                model.addAttribute("checkLogin", false);
            }
        }

        Double doanhThu = hoaDonService.doanhThuThang(startDate, endDate, null);
        Double doanhThuTruoc = hoaDonService.doanhThuThangTruoc();

        Integer donHang = hoaDonService.donHangThang(startDate, endDate);
        Integer donHangTruoc = hoaDonService.donHangThangTruoc();

        Integer soSanPham = hoaDonService.soSanPhamThang(startDate, endDate);
        Integer soSanPhamTruoc = hoaDonService.soSanPhamThangTruoc();

        Integer donHangAll = hoaDonService.donHangAllThang(startDate, endDate);
        Integer donHangHuy = hoaDonService.donHangHuyThang(startDate, endDate);
        Integer donHangHoan = hoaDonService.donHangHoanThang(startDate, endDate);

        model.addAttribute("doanhThuThang", doanhThu);
        model.addAttribute("tyLeDoanhThu", doanhThuTruoc == 0 ? 100 : Math.ceil(doanhThu / doanhThuTruoc * 100));

        model.addAttribute("soDonThang", donHang);
        model.addAttribute("tyLeDonHang", donHangTruoc == 0 ? 100 : Math.ceil(doanhThu / donHangTruoc * 100));

        model.addAttribute("soSanPhamThang", soSanPham);
        model.addAttribute("tyLeSoSanPham", soSanPhamTruoc == 0 ? 100 : Math.ceil((double) soSanPham / soSanPhamTruoc * 100));

        model.addAttribute("doanhThuTrungBinhThang", donHang == 0 ? 0 : Math.ceil(doanhThu / donHang));

        List<KhachHangMuaNhieuNhat> list = hoaDonService.KhachHangMuaNhieuNhat(startDate, endDate);
        list.forEach(s -> s.setPhanTramDoanhThu(Math.ceil((double) s.getTongTien() / doanhThu * 100)));
        model.addAttribute("khachHangMuaNhieuNhat", list.stream().limit(5).toList());

        model.addAttribute("soDonAllThang", donHangAll);
        model.addAttribute("soDonHuyThang", donHangHuy);
        model.addAttribute("soDonHoanThang", donHangHoan);

        model.addAttribute("tyLeDonHangThanhCong", donHangAll == 0 ? 0 : Math.ceil((double) donHang / donHangAll * 100));
        model.addAttribute("tyLeDonHangHuy", donHangAll == 0 ? 0 : Math.ceil((double) donHangHuy / donHangAll * 100));
        model.addAttribute("tyLeDonHangHoan", donHangAll == 0 ? 0 : Math.ceil((double) donHangHoan / donHangAll * 100));

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "ViewNhanVien/DoanhThu/DoanhThu";
    }

}
