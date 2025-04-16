package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.NhanVien;
import com.example.webbanson.model.SanPham;
import com.example.webbanson.service.SanPhamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/nhan-vien/mota-san-pham")
public class MoTaSanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("")
    public String MoTaSanPham(Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        List<SanPham> listSanPham = sanPhamService.getAll(); // mỗi SanPham có id, ten, moTaHtml
        model.addAttribute("listSanPham", listSanPham);

        // Truyền sản phẩm đầu tiên làm mặc định
        if (!listSanPham.isEmpty()) {
            model.addAttribute("moTaSanPham", listSanPham.get(0).getMoTa());
        }
        return "ViewNhanVien/MoTaSanPham/MoTaSanPham";
    }
}
