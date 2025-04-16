package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.NhanVien;
import com.example.webbanson.model.Nsx;
import com.example.webbanson.model.Voucher;
import com.example.webbanson.service.KhachHangService;
import com.example.webbanson.service.VoucherService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping("/nhan-vien/voucher")
public class VoucherConTroller {
    private final VoucherService voucherService;

    private final KhachHangService khachHangService;

    @GetMapping("")
    public String Voucher(@RequestParam(defaultValue = "1") Integer pageNo,
                          Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        Page<Voucher> list = voucherService.findAllIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listVoucher", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", voucherService.getAll().size());
        model.addAttribute("successAdd", false);
        model.addAttribute("successUpdate", false);
        return "ViewNhanVien/Voucher/Voucher";
    }

    @PostMapping("/search")
    public String searchVoucher(@RequestParam(defaultValue = "1") Integer pageNo,
                                @RequestParam(required = false) String tenSearch,
                                @RequestParam(required = false) Integer hinhThucSearch,
                                @RequestParam(required = false) Integer loaiGiamSearch,
                                @RequestParam(required = false) Integer trangThaiSearch,
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
        model.addAttribute("hinhThucSearch", hinhThucSearch);
        model.addAttribute("loaiGiamSearch", loaiGiamSearch);
        model.addAttribute("trangThaiSearch", trangThaiSearch);
        Page<Voucher> list = voucherService.searchVoucher(tenSearch, hinhThucSearch, loaiGiamSearch, trangThaiSearch, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listVoucher", list.getContent());
        model.addAttribute("size", voucherService.countSearchNSX(tenSearch, hinhThucSearch, loaiGiamSearch, trangThaiSearch, PageRequest.of(pageNo - 1, 7)));
        return "ViewNhanVien/Voucher/SearchVoucher";
    }

    @GetMapping("/search")
    public String searchVoucherGet(@RequestParam(defaultValue = "1") Integer pageNo,
                                   @RequestParam(required = false) String tenSearch,
                                   @RequestParam(required = false) Integer hinhThucSearch,
                                   @RequestParam(required = false) Integer loaiGiamSearch,
                                   @RequestParam(required = false) Integer trangThaiSearch,
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
        model.addAttribute("hinhThucSearch", hinhThucSearch);
        model.addAttribute("loaiGiamSearch", loaiGiamSearch);
        model.addAttribute("trangThaiSearch", trangThaiSearch);
        Page<Voucher> list = voucherService.searchVoucher(tenSearch, hinhThucSearch, loaiGiamSearch, trangThaiSearch, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listVoucher", list.getContent());
        model.addAttribute("size", voucherService.countSearchNSX(tenSearch, hinhThucSearch, loaiGiamSearch, trangThaiSearch, PageRequest.of(pageNo - 1, 7)));
        return "ViewNhanVien/Voucher/SearchVoucher";
    }

    @GetMapping("/view-add")
    public String ViewAddVoucher(Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        model.addAttribute("voucher", new Voucher());
        return "ViewNhanVien/Voucher/AddVoucher";
    }

    @PostMapping("/add")
    public String AddVoucher(@Valid Voucher voucher, Errors errors, Model model, @RequestParam(defaultValue = "1") Integer pageNo, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        if (errors.hasErrors()) {
            model.addAttribute("voucher", voucher);
            return "ViewNhanVien/Voucher/AddVoucher";
        }
        List<Voucher> listVoucher = voucherService.getAll();
        boolean exists = listVoucher.stream().anyMatch(ns ->
                ns.getTen().equals(voucher.getTen())
        );
        if (exists) {
            model.addAttribute("tenError", "Voucher đã tồn tại");
            model.addAttribute("voucher", voucher);
            return "ViewNhanVien/Voucher/AddVoucher";
        }
        if (voucher.getGiaTriGiamToiDa() > voucher.getDieuKien()) {
            model.addAttribute("giaTriGiamToiDaError", "Giá trị giảm tối đa phải nhỏ hơn điều kiện");
            model.addAttribute("voucher", voucher);
            return "ViewNhanVien/Voucher/AddVoucher";
        }
        if(voucher.getLoaiGiam()) voucher.setGiaTriGiam(null);
        voucher.setNgayBatDau(LocalDate.now());
        String randomNumber = String.format("%08d", new Random().nextInt(10000000));
        voucher.setMa("VC" + randomNumber);
        voucherService.save(voucher);
        Page<Voucher> list = voucherService.findAllIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listVoucher", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", voucherService.getAll().size());
        model.addAttribute("successAdd", true);
        model.addAttribute("successUpdate", false);
        return "ViewNhanVien/Voucher/Voucher";
    }

    @GetMapping("/detail/{id}")
    public String detailVoucher(@PathVariable Integer id,
                                    Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        model.addAttribute("voucher", voucherService.getOneVoucherById(id));
        return "ViewNhanVien/Voucher/DetailVoucher";
    }

    @PostMapping("/update")
    public String UpdateVoucher(@Valid Voucher voucher, Errors errors, Model model, @RequestParam(defaultValue = "1") Integer pageNo, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        if (errors.hasErrors()) {
            model.addAttribute("voucher", voucher);
            return "ViewNhanVien/Voucher/DetailVoucher";
        }
        List<Voucher> listVoucher = voucherService.getAll();
        boolean exists = listVoucher.stream().filter(v -> v.getId() != voucher.getId()).anyMatch(ns ->
                ns.getTen().equals(voucher.getTen())
        );
        if (exists) {
            model.addAttribute("tenError", "Voucher đã tồn tại");
            model.addAttribute("voucher", voucher);
            return "ViewNhanVien/Voucher/DetailVoucher";
        }
        if (voucher.getGiaTriGiamToiDa() > voucher.getDieuKien()) {
            model.addAttribute("giaTriGiamToiDaError", "Giá trị giảm tối đa phải nhỏ hơn điều kiện");
            model.addAttribute("voucher", voucher);
            return "ViewNhanVien/Voucher/DetailVoucher";
        }
        voucherService.save(voucher);
        Page<Voucher> list = voucherService.findAllIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listVoucher", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", voucherService.getAll().size());
        model.addAttribute("successAdd", false);
        model.addAttribute("successUpdate", true);
        return "ViewNhanVien/Voucher/Voucher";
    }

    @GetMapping("/ap-dung")
    public String addKhachHangVoucher(Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        model.addAttribute("listVoucher", voucherService.getAll());
        model.addAttribute("listKhachHang", khachHangService.fillAll());
        model.addAttribute("listKhachHangNoRank", khachHangService.fillAllNoRank());
        model.addAttribute("listKhachHangRankBronze", khachHangService.fillAllRankBronze());
        model.addAttribute("listKhachHangRankSiliver", khachHangService.fillAllRankSiliver());
        model.addAttribute("listKhachHangRankGold", khachHangService.fillAllRankGold());
        return "ViewNhanVien/Voucher/AddKhachHangVoucher";
    }
}
