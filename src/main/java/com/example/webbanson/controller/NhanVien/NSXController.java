package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.DongSanPham;
import com.example.webbanson.model.NhanVien;
import com.example.webbanson.model.Nsx;
import com.example.webbanson.service.NSXService;
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
@RequestMapping("/nhan-vien/nsx")
public class NSXController {
    private final NSXService nsxService;
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
        Page<Nsx> list = nsxService.getAllNSXByIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listNSX", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", nsxService.getAll().size());
        model.addAttribute("successAdd", false);
        model.addAttribute("successUpdate", false);
        return "ViewNhanVien/NSX/NSX";
    }

    @PostMapping("/search")
    public String searchMauSac(@RequestParam(defaultValue = "1") Integer pageNo,
                               @RequestParam(required = false) String tenSearch,
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
        model.addAttribute("trangThaiSearch", trangThaiSearch);
        Page<Nsx> list = nsxService.searchNSX(tenSearch ,trangThaiSearch, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listNSX", list.getContent());
        model.addAttribute("size", nsxService.countSearchNSX(tenSearch ,trangThaiSearch, PageRequest.of(pageNo -1, 7)));
        return "ViewNhanVien/NSX/SearchNSX";
    }

    @GetMapping("/search")
    public String searchMauSacGet(@RequestParam(defaultValue = "1") Integer pageNo,
                                   @RequestParam(required = false) String tenSearch,
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
        model.addAttribute("trangThaiSearch", trangThaiSearch);
        Page<Nsx> list = nsxService.searchNSX(tenSearch ,trangThaiSearch, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listNSX", list.getContent());
        model.addAttribute("size", nsxService.countSearchNSX(tenSearch ,trangThaiSearch, PageRequest.of(pageNo -1, 7)));
        return "ViewNhanVien/NSX/SearchNSX";
    }

    @GetMapping("/view-add")
    public String ViewAddNSX(Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nsx", new Nsx());
        return "ViewNhanVien/NSX/AddNSX";
    }

    @PostMapping("/add")
    public String AddNSX(@Valid Nsx nsx, Errors errors, Model model, @RequestParam(defaultValue = "1") Integer pageNo, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        if(errors.hasErrors()) {
            model.addAttribute("nsx", nsx);
            return "ViewNhanVien/NSX/AddNSX";
        }
        List<Nsx> listNsx = nsxService.getAll();
        boolean exists = listNsx.stream().anyMatch(ns ->
                ns.getTen().equals(nsx.getTen())
        );
        if(exists) {
            model.addAttribute("tenError", "Nhà sản xuất đã tồn tại");
            model.addAttribute("nsx", nsx);
            return "ViewNhanVien/NSX/AddNSX";
        }
        String randomNumber = String.format("%07d", new Random().nextInt(10000000));
        nsx.setMa("NSX" + randomNumber);
        nsxService.add(nsx);
        Page<Nsx> list = nsxService.getAllNSXByIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listNSX", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", nsxService.getAll().size());
        model.addAttribute("successAdd", true);
        model.addAttribute("successUpdate", false);
        return "ViewNhanVien/NSX/NSX";
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
        model.addAttribute("nsx", nsxService.getOneNSxById(id));
        return "ViewNhanVien/NSX/DetailNSX";
    }

    @PostMapping("/update")
    public String UpdateDongSanPham(@Valid Nsx nsx, Errors  errors, Model model, @RequestParam(defaultValue = "1") Integer pageNo, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        if(errors.hasErrors()) {
            model.addAttribute("nsx", nsx);
            return "ViewNhanVien/NSX/DetailNSX";
        }
        System.out.println(nsx.getId());
        List<Nsx> listNsx = nsxService.getAll();
        boolean exists = listNsx.stream()
                .filter(ms -> ms.getId() != nsx.getId()) // Loại trừ chính nó
                .anyMatch(ms -> ms.getTen().equalsIgnoreCase(nsx.getTen())); // Kiểm tra trùng tên
        if(exists) {
            model.addAttribute("tenError", "Nhà sản xuất đã tồn tại");
            model.addAttribute("nsx", nsx);
            return "ViewNhanVien/NSX/AddNSX";
        }
        nsxService.add(nsx);
        Page<Nsx> list = nsxService.getAllNSXByIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listNSX", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", nsxService.getAll().size());
        model.addAttribute("successAdd", true);
        model.addAttribute("successUpdate", true);
        return "ViewNhanVien/NSX/NSX";
    }
}
