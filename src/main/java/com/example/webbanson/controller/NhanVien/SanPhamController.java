package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.SanPham;
import com.example.webbanson.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping("/nhan-vien")
public class SanPhamController {
    private final SanPhamChiTietService sanPhamChiTietService;
    private final SanPhamService sanPhamService;
    private final MauSacService mauSacService;
    private final KhoiLuongService khoiLuongService;
    private final ChatLongService chatLongService;
    private final NSXService nsxService;
    private final DongSanPhamService dongSanPhamService;
    private final AnhSanPhamService anhSanPhamService;
    private final DanhGiaService danhGiaService;

    @GetMapping("/san-pham")
    public String sanPham(@RequestParam(defaultValue = "1") Integer pageNo,
                             Model model) {
        Page<SanPham> list = sanPhamService.getAllPageSanPham(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listSanPham", list.getContent());
        model.addAttribute("listNSX", nsxService.getAll());
        model.addAttribute("listDongSanPham", dongSanPhamService.getAll());
        model.addAttribute("size", sanPhamService.getAll().size());
        model.addAttribute("successAdd", false);
        model.addAttribute("successUpdate", false);
        return "ViewNhanVien/SanPham/SanPham";
    }

    @PostMapping("/san-pham/search")
    public String searchSanPham(@RequestParam(defaultValue = "1") Integer pageNo,
                                @RequestParam(required = false) Integer idNSX,
                                @RequestParam(required = false) Integer idDongSanPham,
                                @RequestParam(required = false) String tenSearch,
                             Model model) {
        model.addAttribute("idNSX", idNSX);
        model.addAttribute("idDongSanPham", idDongSanPham);
        model.addAttribute("tenSearch", tenSearch);
        Page<SanPham> list = sanPhamService.searchSanPham(tenSearch, idNSX, idDongSanPham, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listSanPham", list.getContent());
        model.addAttribute("listNSX", nsxService.getAll());
        model.addAttribute("listDongSanPham", dongSanPhamService.getAll());
        model.addAttribute("size", sanPhamService.conntSearchSanPham(tenSearch, idNSX, idDongSanPham, PageRequest.of(pageNo - 1, 7)));
        return "ViewNhanVien/SanPham/SearchSanPham";
    }

    @GetMapping("/san-pham/search")
    public String searchSanPhamGet(@RequestParam(defaultValue = "1") Integer pageNo,
                                @RequestParam(required = false) Integer idNSX,
                                @RequestParam(required = false) Integer idDongSanPham,
                                @RequestParam(required = false) String tenSearch,
                             Model model) {
        model.addAttribute("idNSX", idNSX);
        model.addAttribute("idDongSanPham", idDongSanPham);
        model.addAttribute("tenSearch", tenSearch);
        Page<SanPham> list = sanPhamService.searchSanPham(tenSearch, idNSX, idDongSanPham, PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listSanPham", list.getContent());
        model.addAttribute("listNSX", nsxService.getAll());
        model.addAttribute("listDongSanPham", dongSanPhamService.getAll());
        model.addAttribute("size", sanPhamService.conntSearchSanPham(tenSearch, idNSX, idDongSanPham, PageRequest.of(pageNo - 1, 7)));
        return "ViewNhanVien/SanPham/SearchSanPham";
    }



    @GetMapping("/san-pham/view-add")
    public String sanPham(Model model) {
        model.addAttribute("listNSX", nsxService.getNSXConHoatDong());
        model.addAttribute("listDongSanPham", dongSanPhamService.getDongSanPhamConHoatDong());
        model.addAttribute("sanPham", new SanPham());
        return "ViewNhanVien/SanPham/AddSanPham";
    }

    @PostMapping("/san-pham/add")
    public String addSanPham(@RequestParam(defaultValue = "1") Integer pageNo,
                            @Valid SanPham sanPham, Errors errors, Model model,
                             @RequestParam("file") MultipartFile file) {
        if (errors.hasErrors()) {
            model.addAttribute("listNSX", nsxService.getNSXConHoatDong());
            model.addAttribute("listDongSanPham", dongSanPhamService.getDongSanPhamConHoatDong());
            model.addAttribute("sanPham", sanPham);
            return "ViewNhanVien/SanPham/AddSanPham";
        }
        if (!file.isEmpty()) {
            try {
                // Tạo tên file mới
                String randomNumber = String.format("%08d", new Random().nextInt(100000000));
                String fileName = "AnhSanPham" + randomNumber + ".png";
                String filePath = "C:\\Users\\Admin\\Desktop\\WebBanSon\\WebBanSon\\src\\main\\resources\\static\\img\\SanPham\\" + fileName;

                // Lưu file vào thư mục
                File destinationFile = new File(filePath);
                file.transferTo(destinationFile);

                // Lưu đường dẫn vào database
                sanPham.setAnhSanPham("/img/SanPham/" + fileName);

                sanPham.setMa("SP" + randomNumber);
                sanPhamService.add(sanPham);
                model.addAttribute("successAdd", true);
                model.addAttribute("successUpdate", false);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorAnh", "Lỗi khi lưu ảnh!");
                return "ViewNhanVien/SanPham/AddSanPham";
            }
        } else {
            sanPham.setMa("SP" + new Random().nextInt(100000000));
            sanPhamService.add(sanPham);
            model.addAttribute("successAdd", true);
            model.addAttribute("successUpdate", false);
        }
        Page<SanPham> list = sanPhamService.getAllPageSanPham(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listSanPham", list.getContent());
        model.addAttribute("listNSX", nsxService.getAll());
        model.addAttribute("listDongSanPham", dongSanPhamService.getAll());
        model.addAttribute("size", sanPhamService.getAll().size());
        return "ViewNhanVien/SanPham/SanPham";
    }

    @GetMapping("/san-pham/detail/{id}")
    public String detailSanPham(@PathVariable Integer id,
                                Model model) {
        model.addAttribute("sanPham", sanPhamService.getOneSanPhamById(id));
        model.addAttribute("listNSX", nsxService.getNSXConHoatDong());
        model.addAttribute("listDongSanPham", dongSanPhamService.getDongSanPhamConHoatDong());
//        model.addAttribute("listSanPhamChiTiet", sanPhamChiTietService.getAllByIdSanPham(id));
        return "ViewNhanVien/SanPham/DetailSanPham";
    }

    @PostMapping("/san-pham/update")
    public String updateSanPham(@RequestParam(defaultValue = "1") Integer pageNo,
                                @Valid SanPham sanPham, Errors errors, Model model,
                                @RequestParam("file") MultipartFile file) {
        if (errors.hasErrors()) {
            model.addAttribute("listNSX", nsxService.getNSXConHoatDong());
            model.addAttribute("listDongSanPham", dongSanPhamService.getDongSanPhamConHoatDong());
            model.addAttribute("sanPham", sanPham);
            return "ViewNhanVien/SanPham/DetailSanPham";
        }
        if (!file.isEmpty()) {
            try {
                // Tạo tên file mới
                String randomNumber = String.format("%08d", new Random().nextInt(100000000));
                String fileName = "AnhSanPham" + randomNumber + ".png";
                String filePath = "C:\\Users\\Admin\\Desktop\\WebBanSon\\WebBanSon\\src\\main\\resources\\static\\img\\SanPham\\" + fileName;

                // Lưu file vào thư mục
                File destinationFile = new File(filePath);
                file.transferTo(destinationFile);

                // Lưu đường dẫn vào database
                sanPham.setAnhSanPham("/img/SanPham/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorAnh", "Lỗi khi lưu ảnh!");
                System.out.println("Lỗi khi lưu ảnh!");
                return "ViewNhanVien/SanPham/DetailSanPham";
            }
        }
        sanPhamService.update(sanPham);
        model.addAttribute("successAdd", false);
        model.addAttribute("successUpdate", true);
        Page<SanPham> list = sanPhamService.getAllPageSanPham(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("listSanPham", list.getContent());
        model.addAttribute("listNSX", nsxService.getAll());
        model.addAttribute("listDongSanPham", dongSanPhamService.getAll());
        model.addAttribute("size", sanPhamService.getAll().size());
        return "ViewNhanVien/SanPham/SanPham";
    }

}
