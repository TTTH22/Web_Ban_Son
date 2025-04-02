package com.example.webbanson.controller.NhanVien;


import com.example.webbanson.model.KhoiLuong;
import com.example.webbanson.service.KhoiLuongService;
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
@RequestMapping("/nhan-vien/khoi-luong")
public class KhoiLuongController {
    private final KhoiLuongService khoiLuongService;
    @GetMapping("")
    public String KhoiLuong(@RequestParam(defaultValue = "1") Integer pageNo,
                            Model model) {
        Page<KhoiLuong> list = khoiLuongService.getAllKhoiLuongByIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listKhoiLuong", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", khoiLuongService.getAll().size());
        model.addAttribute("successAdd", false);
        model.addAttribute("successUpdate", false);
        return "ViewNhanVien/KhoiLuong/KhoiLuong";
    }

    @PostMapping("/search")
    public String searchKhoiLuong(@RequestParam(defaultValue = "1") Integer pageNo,
                                  @RequestParam(required = false) Integer tenSearch,
                                  @RequestParam(required = false) Integer trangThaiSearch,
                                  Model model) {
        model.addAttribute("tenSearch", tenSearch);
        model.addAttribute("trangThaiSearch", trangThaiSearch);
        Page<KhoiLuong> list = khoiLuongService.searchKhoiLuong(tenSearch, trangThaiSearch, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listKhoiLuong", list.getContent());
        model.addAttribute("size", khoiLuongService.countSearchKhoiLuong(tenSearch, trangThaiSearch, PageRequest.of(pageNo -1, 7)));
        return "ViewNhanVien/KhoiLuong/SearchKhoiLuong";
    }

    @GetMapping("/search")
    public String searchKhoiLuongGet(@RequestParam(defaultValue = "1") Integer pageNo,
                                  @RequestParam(required = false) Integer tenSearch,
                                  @RequestParam(required = false) Integer trangThaiSearch,
                                  Model model) {
        model.addAttribute("tenSearch", tenSearch);
        model.addAttribute("trangThaiSearch", trangThaiSearch);
        Page<KhoiLuong> list = khoiLuongService.searchKhoiLuong(tenSearch, trangThaiSearch, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listKhoiLuong", list.getContent());
        model.addAttribute("size", khoiLuongService.countSearchKhoiLuong(tenSearch, trangThaiSearch, PageRequest.of(pageNo -1, 7)));
        return "ViewNhanVien/KhoiLuong/SearchKhoiLuong";
    }

    @GetMapping("/view-add")
    public String ViewAddKhoiLuong(Model model) {
        model.addAttribute("khoiLuong", new KhoiLuong());
        return "ViewNhanVien/KhoiLuong/AddKhoiLuong";
    }

    @PostMapping("/add")
    public String AddKhoiLuong(@Valid KhoiLuong khoiLuong, Errors  errors, Model model, @RequestParam(defaultValue = "1") Integer pageNo) {
        if(errors.hasErrors()) {
            model.addAttribute("khoiLuong", khoiLuong);
            return "ViewNhanVien/KhoiLuong/AddKhoiLuong";
        }
        if(khoiLuongService.existsByTen(khoiLuong.getTen())) {
            model.addAttribute("khoiLuongError", "Khối lượng đã tồn tại");
            model.addAttribute("khoiLuong", khoiLuong);
            return "ViewNhanVien/KhoiLuong/AddKhoiLuong";
        }
        String randomNumber = String.format("%08d", new Random().nextInt(100000000));
        khoiLuong.setMa("KL" + randomNumber);
        khoiLuongService.add(khoiLuong);
        model.addAttribute("successAdd", true);
        model.addAttribute("successUpdate", false);
        Page<KhoiLuong> list = khoiLuongService.getAllKhoiLuongByIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listKhoiLuong", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", khoiLuongService.getAll().size());
        return "ViewNhanVien/KhoiLuong/KhoiLuong";
    }

    @GetMapping("/detail/{id}")
    public String detailSanPham(@PathVariable Integer id,
                                Model model) {
        model.addAttribute("khoiLuong", khoiLuongService.getOneSanPhamById(id));
        return "ViewNhanVien/KhoiLuong/DetailKhoiLuong";
    }

    @PostMapping("/update")
    public String UpdateKhoiLuong(@Valid KhoiLuong khoiLuong, Errors  errors, Model model, @RequestParam(defaultValue = "1") Integer pageNo) {
        if(errors.hasErrors()) {
            model.addAttribute("khoiLuong", khoiLuong);
            return "ViewNhanVien/KhoiLuong/DetailKhoiLuong";
        }
        List<KhoiLuong> listKL = khoiLuongService.getAll();
        boolean exists = listKL.stream().anyMatch(kl ->
                kl.getTen().equals(khoiLuong.getTen()) &&
                        !kl.getId().equals(khoiLuong.getId())
        );

        if (exists) {
            model.addAttribute("khoiLuongError", "Khối lượng đã tồn tại");
            model.addAttribute("khoiLuong", khoiLuong);
            return "ViewNhanVien/KhoiLuong/DetailKhoiLuong";
        }
        khoiLuongService.update(khoiLuong);
        model.addAttribute("successAdd", false);
        model.addAttribute("successUpdate", true);
        Page<KhoiLuong> list = khoiLuongService.getAllKhoiLuongByIdDesc(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listKhoiLuong", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", khoiLuongService.getAll().size());
        return "ViewNhanVien/KhoiLuong/KhoiLuong";
    }

}
