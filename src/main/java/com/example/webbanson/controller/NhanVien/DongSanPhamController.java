package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.DongSanPham;
import com.example.webbanson.model.KhoiLuong;
import com.example.webbanson.model.MauSac;
import com.example.webbanson.model.NhanVien;
import com.example.webbanson.service.DongSanPhamService;
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
@RequestMapping("/nhan-vien/dong-san-pham")
public class DongSanPhamController {
    private final DongSanPhamService dongSanPhamService;
    @GetMapping("")
    public String MauSac(@RequestParam(defaultValue = "1") Integer pageNo,
                         Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        if (nhanVien != null) {
            model.addAttribute("nhanVien", nhanVien);
            if (nhanVien.getChucVu().equals("Quản lý")) {
                model.addAttribute("checkLogin", true);
            } else {
                model.addAttribute("checkLogin", false);
            }
        }
        Page<DongSanPham> list = dongSanPhamService.getAllDongSanPhamByIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listDongSanPham", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", dongSanPhamService.getAll().size());
        model.addAttribute("successAdd", false);
        model.addAttribute("successUpdate", false);
        return "ViewNhanVien/DongSanPham/DongSanPham";
    }

    @PostMapping("/search")
    public String searchDongSanPham(@RequestParam(defaultValue = "1") Integer pageNo,
                               @RequestParam(required = false) String tenSearch,
                               @RequestParam(required = false) String maSearch,
                               @RequestParam(required = false) Integer trangThaiSearch,
                               Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        if (nhanVien != null) {
            model.addAttribute("nhanVien", nhanVien);
            if (nhanVien.getChucVu().equals("Quản lý")) {
                model.addAttribute("checkLogin", true);
            } else {
                model.addAttribute("checkLogin", false);
            }
        }
        model.addAttribute("tenSearch", tenSearch);
        model.addAttribute("trangThaiSearch", trangThaiSearch);
        Page<DongSanPham> list = dongSanPhamService.searchDongSanPham(tenSearch ,trangThaiSearch, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listDongSanPham", list.getContent());
        model.addAttribute("size", dongSanPhamService.countSearchDongSanPham(tenSearch,trangThaiSearch, PageRequest.of(pageNo -1, 7)));
        return "ViewNhanVien/DongSanPham/SearchDongSanPham";
    }

    @GetMapping("/search")
    public String searchDongSanPhamGet(@RequestParam(defaultValue = "1") Integer pageNo,
                                  @RequestParam(required = false) String tenSearch,
                                  @RequestParam(required = false) String maSearch,
                                  @RequestParam(required = false) Integer trangThaiSearch,
                                  Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        if (nhanVien != null) {
            model.addAttribute("nhanVien", nhanVien);
            if (nhanVien.getChucVu().equals("Quản lý")) {
                model.addAttribute("checkLogin", true);
            } else {
                model.addAttribute("checkLogin", false);
            }
        }
        model.addAttribute("tenSearch", tenSearch);
        model.addAttribute("trangThaiSearch", trangThaiSearch);
        Page<DongSanPham> list = dongSanPhamService.searchDongSanPham(tenSearch,trangThaiSearch, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listDongSanPham", list.getContent());
        model.addAttribute("size", dongSanPhamService.countSearchDongSanPham(tenSearch,trangThaiSearch, PageRequest.of(pageNo -1, 7)));
        return "ViewNhanVien/DongSanPham/SearchDongSanPham";
    }

    @GetMapping("/view-add")
    public String ViewAddDongSanPham(Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        if (nhanVien != null) {
            model.addAttribute("nhanVien", nhanVien);
            if (nhanVien.getChucVu().equals("Quản lý")) {
                model.addAttribute("checkLogin", true);
            } else {
                model.addAttribute("checkLogin", false);
            }
        }
        model.addAttribute("dongSanPham", new DongSanPham());
        return "ViewNhanVien/DongSanPham/AddDongSanPham";
    }

    @PostMapping("/add")
    public String AddDongSanPham(@Valid DongSanPham dongSanPham, Errors errors, Model model, @RequestParam(defaultValue = "1") Integer pageNo, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        if (nhanVien != null) {
            model.addAttribute("nhanVien", nhanVien);
            if (nhanVien.getChucVu().equals("Quản lý")) {
                model.addAttribute("checkLogin", true);
            } else {
                model.addAttribute("checkLogin", false);
            }
        }
        if(errors.hasErrors()) {
            model.addAttribute("dongSanPham", dongSanPham);
            return "ViewNhanVien/DongSanPham/AddDongSanPham";
        }
        List<DongSanPham> listDsp = dongSanPhamService.getAll();
        boolean exists = listDsp.stream().anyMatch(ms ->
                ms.getTen().equals(dongSanPham.getTen())
        );
        if(exists) {
            model.addAttribute("tenError", "Dòng sản phẩm đã tồn tại");
            model.addAttribute("dongSanPham", dongSanPham);
            return "ViewNhanVien/DongSanPham/AddDongSanPham";
        }
        String randomNumber = String.format("%07d", new Random().nextInt(10000000));
        dongSanPham.setMa("DSP" + randomNumber);
        dongSanPhamService.add(dongSanPham);
        Page<DongSanPham> list = dongSanPhamService.getAllDongSanPhamByIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listDongSanPham", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", dongSanPhamService.getAll().size());
        model.addAttribute("successAdd", true);
        model.addAttribute("successUpdate", false);
        return "ViewNhanVien/DongSanPham/DongSanPham";
    }

    @GetMapping("/detail/{id}")
    public String detailDongSanPham(@PathVariable Integer id,
                                Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        if (nhanVien != null) {
            model.addAttribute("nhanVien", nhanVien);
            if (nhanVien.getChucVu().equals("Quản lý")) {
                model.addAttribute("checkLogin", true);
            } else {
                model.addAttribute("checkLogin", false);
            }
        }
        model.addAttribute("dongSanPham", dongSanPhamService.getOneDongSanPhamById(id));
        return "ViewNhanVien/DongSanPham/DetailDongSanPham";
    }

    @PostMapping("/update")
    public String UpdateDongSanPham(@Valid DongSanPham dongSanPham, Errors  errors, Model model, @RequestParam(defaultValue = "1") Integer pageNo, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        if (nhanVien != null) {
            model.addAttribute("nhanVien", nhanVien);
            if (nhanVien.getChucVu().equals("Quản lý")) {
                model.addAttribute("checkLogin", true);
            } else {
                model.addAttribute("checkLogin", false);
            }
        }
        if(errors.hasErrors()) {
            model.addAttribute("dongSanPham", dongSanPham);
            return "ViewNhanVien/DongSanPham/DetailDongSanPham";
        }
        List<DongSanPham> listDsp = dongSanPhamService.getAll();
        boolean exists = listDsp.stream().anyMatch(kl ->
                kl.getTen().equals(dongSanPham.getTen()) &&
                        !kl.getId().equals(dongSanPham.getId())
        );

        if (exists) {
            model.addAttribute("DongSanPhamError", "Dòng sản phẩm đã tồn tại");
            model.addAttribute("dongSanPham", dongSanPham);
            return "ViewNhanVien/DongSanPham/DetailDongSanPham";
        }
        dongSanPhamService.add(dongSanPham);
        Page<DongSanPham> list = dongSanPhamService.getAllDongSanPhamByIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listDongSanPham", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", dongSanPhamService.getAll().size());
        model.addAttribute("successAdd", false);
        model.addAttribute("successUpdate", true);
        return "ViewNhanVien/DongSanPham/DongSanPham";
    }
}
