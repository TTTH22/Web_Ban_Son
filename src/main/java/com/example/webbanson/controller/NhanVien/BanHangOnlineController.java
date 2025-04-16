package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.HoaDon;
import com.example.webbanson.model.NhanVien;
import com.example.webbanson.model.Nsx;
import com.example.webbanson.service.DiaChiService;
import com.example.webbanson.service.HoaDonService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/nhan-vien/ban-hang-online")
public class BanHangOnlineController {
    private final HoaDonService hoaDonService;
    private final DiaChiService diaChiService;

    @GetMapping("")
    public String BanHangOnline(Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        List<HoaDon> listChoXacNhan = hoaDonService.getAllHoaDonChoXacNhan();
        List<HoaDon> listDangGiao = hoaDonService.listAllHoaDonDangGiao();
        List<HoaDon> listDaGiao = hoaDonService.listAllHoaDonDaGiao();
        List<HoaDon> listChoHoan = hoaDonService.listAllHoaDonChoHoan();
        List<HoaDon> listDaHoan = hoaDonService.listAllHoaDonDaHoan();
        List<HoaDon> listDaHuy = hoaDonService.listAllHoaDonDaHuy();
        model.addAttribute("listChoXacNhan", listChoXacNhan);
        model.addAttribute("listDangGiao", listDangGiao);
        model.addAttribute("listDaGiao", listDaGiao);
        model.addAttribute("listChoHoan", listChoHoan);
        model.addAttribute("listDaHoan", listDaHoan);
        model.addAttribute("listDaHuy", listDaHuy);
        return "ViewNhanVien/BanHangOnline/BanHangOnline";
    }

     @GetMapping("/detail/{id}")
    public String detailBanHangOnline(@PathVariable Integer id,
                                    Model model, HttpSession session) {
         NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
         model.addAttribute("nhanVien", nhanVien);
         if(nhanVien.getChucVu().equals("Quản lý")) {
             model.addAttribute("checkLogin", true);
         }
         else {
             model.addAttribute("checkLogin", false);
         }
        model.addAttribute("banHang", hoaDonService.getOneBanHangOnlineById(id));
        return "ViewNhanVien/BanHangOnline/DetailBanHangOnline";
    }
}
