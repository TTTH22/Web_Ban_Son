package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.KhoiLuong;
import com.example.webbanson.model.MauSac;
import com.example.webbanson.model.NhanVien;
import com.example.webbanson.service.MauSacService;
import jakarta.servlet.http.HttpSession;
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
@RequestMapping("/nhan-vien/mau-sac")
public class MauSacController {
    private final MauSacService mauSacService;
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
        Page<MauSac> list = mauSacService.getAllMauSacByIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listMauSac", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", mauSacService.getAll().size());
        model.addAttribute("successAdd", false);
        model.addAttribute("successUpdate", false);
        return "ViewNhanVien/MauSac/MauSac";
    }

    @PostMapping("/search")
    public String searchMauSac(@RequestParam(defaultValue = "1") Integer pageNo,
                                  @RequestParam(required = false) String tenSearch,
                                  @RequestParam(required = false) String maSearch,
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
        model.addAttribute("maSearch", maSearch);
        model.addAttribute("trangThaiSearch", trangThaiSearch);
        Page<MauSac> list = mauSacService.searchMauSac(tenSearch, maSearch,trangThaiSearch, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listMauSac", list.getContent());
        model.addAttribute("size", mauSacService.countSearchMauSac(tenSearch, maSearch,trangThaiSearch, PageRequest.of(pageNo -1, 7)));
        return "ViewNhanVien/MauSac/SearchMauSac";
    }

    @GetMapping("/search")
    public String searchMauSacGet(@RequestParam(defaultValue = "1") Integer pageNo,
                                  @RequestParam(required = false) String tenSearch,
                                  @RequestParam(required = false) String maSearch,
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
        model.addAttribute("maSearch", maSearch);
        model.addAttribute("trangThaiSearch", trangThaiSearch);
        Page<MauSac> list = mauSacService.searchMauSac(tenSearch, maSearch,trangThaiSearch, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listMauSac", list.getContent());
        model.addAttribute("size", mauSacService.countSearchMauSac(tenSearch, maSearch,trangThaiSearch, PageRequest.of(pageNo -1, 7)));
        return "ViewNhanVien/MauSac/SearchMauSac";
    }

    @GetMapping("/view-add")
    public String ViewAddMauSac(Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        model.addAttribute("mauSac", new KhoiLuong());
        return "ViewNhanVien/MauSac/AddMauSac";
    }

    @PostMapping("/add")
    public String AddMauSac(@Valid MauSac mauSac, Errors errors, Model model, @RequestParam(defaultValue = "1") Integer pageNo, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        if(errors.hasErrors()) {
            model.addAttribute("mauSac", mauSac);
            return "ViewNhanVien/MauSac/AddMauSac";
        }
        List<MauSac> listMS = mauSacService.getAll();
        boolean exists = listMS.stream().anyMatch(ms ->
                ms.getTen().equals(mauSac.getTen())
        );
        if(exists) {
            model.addAttribute("tenError", "Màu sắc đã tồn tại");
            model.addAttribute("mauSac", mauSac);
            return "ViewNhanVien/MauSac/AddMauSac";
        }
        boolean existsMa = listMS.stream().anyMatch(ms ->
                ms.getMa().equals(mauSac.getMa())
        );
        if(existsMa) {
            model.addAttribute("maError", "Mã màu sắc đã tồn tại");
            model.addAttribute("mauSac", mauSac);
            return "ViewNhanVien/MauSac/AddMauSac";
        }
        mauSacService.add(mauSac);
        model.addAttribute("successAdd", true);
        model.addAttribute("successUpdate", false);
        Page<MauSac> list = mauSacService.getAllMauSacByIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listMauSac", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", mauSacService.getAll().size());
        return "ViewNhanVien/MauSac/MauSac";
    }

    @GetMapping("/detail/{id}")
    public String detailDongSanPham(@PathVariable Integer id,
                                    Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        model.addAttribute("mauSac", mauSacService.getOneMauSacById(id));
        return "ViewNhanVien/MauSac/DetailMauSac";
    }

    @PostMapping("/update")
    public String UpdateMauSac(@Valid MauSac mauSac, Errors errors, Model model, @RequestParam(defaultValue = "1") Integer pageNo, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        if(errors.hasErrors()) {
            model.addAttribute("mauSac", mauSac);
            return "ViewNhanVien/MauSac/DetailMauSac";
        }
        System.out.println(mauSac.getId());
        List<MauSac> listMS = mauSacService.getAll();
        boolean exists = listMS.stream()
                .filter(ms -> ms.getId() != mauSac.getId()) // Loại trừ chính nó
                .anyMatch(ms -> ms.getTen().equalsIgnoreCase(mauSac.getTen())); // Kiểm tra trùng tên
        if(exists) {
            model.addAttribute("tenError", "Màu sắc đã tồn tại");
            model.addAttribute("mauSac", mauSac);
            return "ViewNhanVien/MauSac/DetailMauSac";
        }
        boolean existsMa = listMS.stream().anyMatch(ms ->
                ms.getMa().equals(mauSac.getMa()) &&
                        ms.getId() != (mauSac.getId())
        );
        if(existsMa) {
            model.addAttribute("maError", "Mã màu sắc đã tồn tại");
            model.addAttribute("mauSac", mauSac);
            return "ViewNhanVien/MauSac/DetailMauSac";
        }
        mauSacService.add(mauSac);
        model.addAttribute("successAdd", true);
        model.addAttribute("successUpdate", false);
        Page<MauSac> list = mauSacService.getAllMauSacByIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listMauSac", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", mauSacService.getAll().size());
        return "ViewNhanVien/MauSac/MauSac";
    }

}
