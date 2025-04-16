package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.KhachHang;
import com.example.webbanson.model.NhanVien;
import com.example.webbanson.model.Nsx;
import com.example.webbanson.service.KhachHangService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/nhan-vien/khach-hang")
public class KhachHangController {
    private final KhachHangService khachHangService;

    @GetMapping("")
    public String MauSac(@RequestParam(defaultValue = "1") Integer pageNo,
                         Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        Page<KhachHang> list = khachHangService.getAll(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listKhachHang", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", list.getTotalElements());
        model.addAttribute("successAdd", false);
        model.addAttribute("successUpdate", false);
        return "ViewNhanVien/KhachHang/KhachHang";
    }

    @PostMapping("/search")
    public String searchKhachHang(@RequestParam(defaultValue = "1") Integer pageNo,
                               @RequestParam(required = false) String tenSearch,
                               @RequestParam(required = false) String sdtSearch,
                               Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        model.addAttribute("tenSearch", tenSearch);
        model.addAttribute("sdtSearch", sdtSearch);
        Page<KhachHang> list = khachHangService.searchKhachHang(tenSearch ,sdtSearch, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listKhachHang", list.getContent());
        model.addAttribute("size", khachHangService.countSearchKhachHang(tenSearch ,sdtSearch, PageRequest.of(pageNo -1, 7)));
        return "ViewNhanVien/KhachHang/SearchKhachHang";
    }

    @GetMapping("/search")
    public String searchKhachHangGet(@RequestParam(defaultValue = "1") Integer pageNo,
                               @RequestParam(required = false) String tenSearch,
                               @RequestParam(required = false) String sdtSearch,
                               Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        model.addAttribute("tenSearch", tenSearch);
        model.addAttribute("sdtSearch", sdtSearch);
        Page<KhachHang> list = khachHangService.searchKhachHang(tenSearch ,sdtSearch, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listKhachHang", list.getContent());
        model.addAttribute("size", khachHangService.countSearchKhachHang(tenSearch ,sdtSearch, PageRequest.of(pageNo -1, 7)));
        return "ViewNhanVien/KhachHang/SearchKhachHang";
    }

    @GetMapping("/detail/{id}")
    public String detailKhachHang(@PathVariable Integer id,
                                    Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        model.addAttribute("khachHang", khachHangService.getOneKhachHangById(id));
        return "ViewNhanVien/KhachHang/DetailKhachHang";
    }

}
