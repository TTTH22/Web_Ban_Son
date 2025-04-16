package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.DanhGia;
import com.example.webbanson.model.NhanVien;
import com.example.webbanson.model.SanPham;
import com.example.webbanson.service.DanhGiaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/nhan-vien/danh-gia")
public class DanhGiaController {

    @Autowired
    private DanhGiaService danhGiaService;

    @GetMapping("")
    public String DanhGia(Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        model.addAttribute("listDanhGiaChuaXacNhan", danhGiaService.getAllDanhGiaChuaXacNhan());
        model.addAttribute("listDanhGiaDaXacNhan", danhGiaService.getAllDanhGiaDaXacNhan());
        model.addAttribute("countDanhGiaChuaXacNhan", danhGiaService.countDanhGiaChuaXacNhan());
        model.addAttribute("countDanhGiaDaXacNhan", danhGiaService.countDanhGiaDaXacNhan());
        model.addAttribute("listSanPhamDaDanhGiaAll", danhGiaService.listSanPhamDaDanhGiaAll());
        model.addAttribute("suscessAdd", model.getAttribute("successAdd"));
        model.addAttribute("suscessDelete", model.getAttribute("successDelete"));
        return "ViewNhanVien/DanhGia/DanhGia";
    }

    @GetMapping("/search")
    public String viewDanhGiaPage(Model model,
                                  @RequestParam(required = false) Integer productId,
                                  @RequestParam(required = false) Integer rating, HttpSession session) {

        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        List<DanhGia> danhGias = danhGiaService.findByFilter(productId, rating);

        model.addAttribute("listDanhGiaChuaXacNhan", danhGiaService.getAllDanhGiaChuaXacNhan());
        model.addAttribute("countDanhGiaChuaXacNhan", danhGiaService.countDanhGiaChuaXacNhan());
        model.addAttribute("countDanhGiaDaXacNhan", danhGiaService.countDanhGiaDaXacNhan());
        model.addAttribute("listSanPhamDaDanhGiaAll", danhGiaService.listSanPhamDaDanhGiaAll());
        model.addAttribute("productId", productId);
        model.addAttribute("rating", rating);
        model.addAttribute("listDanhGiaDaXacNhan", danhGias);

        return "ViewNhanVien/DanhGia/DanhGia";
    }

    @GetMapping("/xac-nhan/{id}")
    public String xacNhanDanhGia(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        DanhGia danhGia = danhGiaService.getById(id);
        danhGia.setTrangThai(true);
        danhGiaService.save(danhGia);
        redirectAttributes.addAttribute("successAdd", true);
        return "redirect:/nhan-vien/danh-gia";
    }

    @GetMapping("/xoa/{id}")
    public String xoaDanhGia(@PathVariable Integer id, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        DanhGia danhGia = danhGiaService.getById(id);
        danhGiaService.delete(danhGia);
        redirectAttributes.addAttribute("successDelete", true);
        return "redirect:/nhan-vien/danh-gia";
    }

}
