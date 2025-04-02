package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.DongSanPham;
import com.example.webbanson.model.Nsx;
import com.example.webbanson.model.SanPhamChiTiet;
import com.example.webbanson.service.KhoiLuongService;
import com.example.webbanson.service.MauSacService;
import com.example.webbanson.service.SanPhamChiTietService;
import com.example.webbanson.service.SanPhamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping("/nhan-vien/mat-hang")
public class MatHangController {
    private final SanPhamChiTietService sanPhamChiTietService;
    private final SanPhamService sanPhamService;
    private final MauSacService mauSacService;
    private final KhoiLuongService khoiLuongService;
    @GetMapping("")
    public String MauSac(@RequestParam(defaultValue = "1") Integer pageNo,
                         Model model) {
        Page<SanPhamChiTiet> list = sanPhamChiTietService.getAllSanPhamChiTietByIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listMatHang", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", sanPhamChiTietService.getAll().size());
        model.addAttribute("listSanPham", sanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listKhoiLuong", khoiLuongService.getAll());
        model.addAttribute("successAdd", false);
        model.addAttribute("successUpdate", false);
        return "ViewNhanVien/MatHang/MatHang";
    }

    @PostMapping("/search")
    public String searchMatHang(@RequestParam(defaultValue = "1") Integer pageNo,
                               @RequestParam(required = false) Integer idSanPham,
                               @RequestParam(required = false) Integer idMauSac,
                               @RequestParam(required = false) Integer idKhoiLuong,
                               Model model) {
        model.addAttribute("idSanPham", idSanPham);
        model.addAttribute("idMauSac", idMauSac);
        model.addAttribute("idKhoiLuong", idKhoiLuong);
        Page<SanPhamChiTiet> list = sanPhamChiTietService.searchSPCT(idSanPham ,idMauSac, idKhoiLuong, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listMatHang", list.getContent());
        model.addAttribute("listSanPham", sanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listKhoiLuong", khoiLuongService.getAll());
        model.addAttribute("size", sanPhamChiTietService.countSearchSPCT(idSanPham ,idMauSac, idKhoiLuong, PageRequest.of(pageNo -1, 7)));
        return "ViewNhanVien/MatHang/SearchMatHang";
    }

    @GetMapping("/search")
    public String searchMatHangGet(@RequestParam(defaultValue = "1") Integer pageNo,
                               @RequestParam(required = false) Integer idSanPham,
                               @RequestParam(required = false) Integer idMauSac,
                               @RequestParam(required = false) Integer idKhoiLuong,
                               Model model) {
        model.addAttribute("idSanPham", idSanPham);
        model.addAttribute("idMauSac", idMauSac);
        model.addAttribute("idKhoiLuong", idKhoiLuong);
        Page<SanPhamChiTiet> list = sanPhamChiTietService.searchSPCT(idSanPham ,idMauSac, idKhoiLuong, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listMatHang", list.getContent());
        model.addAttribute("listSanPham", sanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listKhoiLuong", khoiLuongService.getAll());
        model.addAttribute("size", sanPhamChiTietService.countSearchSPCT(idSanPham ,idMauSac, idKhoiLuong, PageRequest.of(pageNo -1, 7)));
        return "ViewNhanVien/MatHang/SearchMatHang";
    }

    @GetMapping("/view-add")
    public String ViewAddMatHang(Model model) {
        model.addAttribute("spct", new SanPhamChiTiet());
        model.addAttribute("listSanPham", sanPhamService.getAllConSuDung());
        model.addAttribute("listMauSac", mauSacService.getAllConSuDung());
        model.addAttribute("listKhoiLuong", khoiLuongService.getAllKhoiLuongConSuDung());
        return "ViewNhanVien/MatHang/AddMatHang";
    }

    @PostMapping("/add")
    public String AddMatHang(@Valid @ModelAttribute("spct") SanPhamChiTiet spct, Errors errors, Model model, @RequestParam(defaultValue = "1") Integer pageNo) {
        if(errors.hasErrors()) {
            model.addAttribute("spct", spct);
            model.addAttribute("listSanPham", sanPhamService.getAllConSuDung());
            model.addAttribute("listMauSac", mauSacService.getAllConSuDung());
            model.addAttribute("listKhoiLuong", khoiLuongService.getAllKhoiLuongConSuDung());
            return "ViewNhanVien/MatHang/AddMatHang";
        }
        List<SanPhamChiTiet> listSpct = sanPhamChiTietService.getAll();
        boolean exists = listSpct.stream().anyMatch(ns ->
                ns.getIdSanPham().getId().equals(spct.getIdSanPham().getId()) &&
                ns.getIdMauSac().getId().equals(spct.getIdMauSac().getId()) &&
                ns.getIdKhoiLuong().getId().equals(spct.getIdKhoiLuong().getId())
        );
        if(exists) {
            model.addAttribute("tenError", "Mặt hàng đã tồn tại");
            model.addAttribute("listSanPham", sanPhamService.getAllConSuDung());
            model.addAttribute("listMauSac", mauSacService.getAllConSuDung());
            model.addAttribute("listKhoiLuong", khoiLuongService.getAllKhoiLuongConSuDung());
            model.addAttribute("spct", spct);
            return "ViewNhanVien/MatHang/AddMatHang";
        }
        if(spct.getDonGiaGiam() > spct.getDonGia()) {
            model.addAttribute("donGiaGiamError", "Đơn giá giảm không được lớn hơn đơn giá");
            model.addAttribute("spct", spct);
            return "ViewNhanVien/MatHang/AddMatHang";
        }
        String randomNumber = String.format("%06d", new Random().nextInt(1000000));
        spct.setMa("SPCT" + randomNumber);
        sanPhamChiTietService.add(spct);
        Page<SanPhamChiTiet> list = sanPhamChiTietService.getAllSanPhamChiTietByIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listMatHang", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", sanPhamChiTietService.getAll().size());
        model.addAttribute("listSanPham", sanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listKhoiLuong", khoiLuongService.getAll());
        model.addAttribute("successAdd", true);
        model.addAttribute("successUpdate", false);
        return "ViewNhanVien/MatHang/MatHang";
    }

    @GetMapping("/detail/{id}")
    public String DetailMatHang(@PathVariable Integer id,Model model) {
        model.addAttribute("spct", sanPhamChiTietService.getOneSanPhamChiTietById(id));
        model.addAttribute("listSanPham", sanPhamService.getAllConSuDung());
        model.addAttribute("listMauSac", mauSacService.getAllConSuDung());
        model.addAttribute("listKhoiLuong", khoiLuongService.getAllKhoiLuongConSuDung());
        return "ViewNhanVien/MatHang/DetailMatHang";
    }

    @PostMapping("/update")
    public String UpdateMatHang(@Valid @ModelAttribute("spct") SanPhamChiTiet spct, Errors errors, Model model, @RequestParam(defaultValue = "1") Integer pageNo) {
        if(errors.hasErrors()) {
            model.addAttribute("spct", spct);
            model.addAttribute("listSanPham", sanPhamService.getAllConSuDung());
            model.addAttribute("listMauSac", mauSacService.getAllConSuDung());
            model.addAttribute("listKhoiLuong", khoiLuongService.getAllKhoiLuongConSuDung());
            return "ViewNhanVien/MatHang/AddMatHang";
        }
        List<SanPhamChiTiet> listSpct = sanPhamChiTietService.getAll();
        boolean exists = listSpct.stream().filter(sp -> !sp.getId().equals(spct.getId())).anyMatch(ns ->
                ns.getIdSanPham().getId().equals(spct.getIdSanPham().getId()) &&
                        ns.getIdMauSac().getId().equals(spct.getIdMauSac().getId()) &&
                        ns.getIdKhoiLuong().getId().equals(spct.getIdKhoiLuong().getId())
        );
        if(exists) {
            model.addAttribute("tenError", "Mặt hàng đã tồn tại");
            model.addAttribute("listSanPham", sanPhamService.getAllConSuDung());
            model.addAttribute("listMauSac", mauSacService.getAllConSuDung());
            model.addAttribute("listKhoiLuong", khoiLuongService.getAllKhoiLuongConSuDung());
            model.addAttribute("spct", spct);
            return "ViewNhanVien/MatHang/AddMatHang";
        }
        if(spct.getDonGiaGiam() > spct.getDonGia()) {
            model.addAttribute("donGiaGiamError", "Đơn giá giảm không được lớn hơn đơn giá");
            model.addAttribute("spct", spct);
            return "ViewNhanVien/MatHang/AddMatHang";
        }
        String randomNumber = String.format("%06d", new Random().nextInt(1000000));
        spct.setMa("SPCT" + randomNumber);
        sanPhamChiTietService.add(spct);
        Page<SanPhamChiTiet> list = sanPhamChiTietService.getAllSanPhamChiTietByIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listMatHang", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", sanPhamChiTietService.getAll().size());
        model.addAttribute("listSanPham", sanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listKhoiLuong", khoiLuongService.getAll());
        model.addAttribute("successAdd", true);
        model.addAttribute("successUpdate", false);
        return "ViewNhanVien/MatHang/MatHang";
    }
}
