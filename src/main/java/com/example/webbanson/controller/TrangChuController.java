package com.example.webbanson.controller;

import com.example.webbanson.dto.SanPhamThongTinDTO;
import com.example.webbanson.model.*;
import com.example.webbanson.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class TrangChuController {

    private final SanPhamChiTietService sanPhamChiTietService;
    private final SanPhamService sanPhamService;
    private final MauSacService mauSacService;
    private final KhoiLuongService khoiLuongService;
    private final ChatLongService chatLongService;
    private final NSXService nsxService;
    private final DongSanPhamService dongSanPhamService;
    private final AnhSanPhamService anhSanPhamService;

    @GetMapping("/trang-chu")
    public String trangChu(Model model) {

        //        Sản phẩm nổi bật
        model.addAttribute("listTop12SanPhamBanChayTrang1", sanPhamService.getTop12SanPhamBanChay(PageRequest.of(0, 4)));
        model.addAttribute("listTop12SanPhamBanChayTrang2", sanPhamService.getTop12SanPhamBanChay(PageRequest.of(4, 4)));
        model.addAttribute("listTop12SanPhamBanChayTrang3", sanPhamService.getTop12SanPhamBanChay(PageRequest.of(8, 4)));

        //        Sản phẩm giảm giá
        model.addAttribute("listSanPhamGiamGiaTrang1", sanPhamService.getTop16SanPhamGiamGia(PageRequest.of(0, 8)));
        model.addAttribute("listSanPhamGiamGiaTrang2", sanPhamService.getTop16SanPhamGiamGia(PageRequest.of(8, 8)));
        return "ViewKhachHang/TrangChu";
    }

    @GetMapping("/san-pham")
    public String sanPham(@RequestParam(defaultValue = "1") Integer pageNo, HttpServletRequest request, Model model) {
        Pageable pageable = PageRequest.of(pageNo - 1, 9);
        model.addAttribute("selectedColors", new ArrayList<>());
        model.addAttribute("selectedNSX", new ArrayList<>());


        model.addAttribute("isSearch", false);
        Page<SanPhamThongTinDTO> list = sanPhamService.getAllPage(pageable);
        model.addAttribute("listSanPham", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("maxPage", list.getTotalPages());
        model.addAttribute("minPriceRange", sanPhamChiTietService.minPrice());
        model.addAttribute("maxPriceRange", sanPhamChiTietService.maxPrice());
        model.addAttribute("startMin", sanPhamChiTietService.minPrice());
        model.addAttribute("startMax", sanPhamChiTietService.maxPrice());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listNSX", nsxService.getAll());
        model.addAttribute("listDongSanPham", dongSanPhamService.getAll());
        return "ViewKhachHang/SanPham";
    }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "1") Integer pageNo,
                         HttpServletRequest request,
                         @RequestParam(required = false) Float minPrice,
                         @RequestParam(required = false) Float maxPrice,
                         @RequestParam(required = false) List<Integer> idMauSac,
                         @RequestParam(required = false) List<Integer> idNsx,
                         @RequestParam(required = false) Integer category,
                         @RequestParam(required = false) String tenSearch,
                         @RequestParam(required = false) Integer idDongSanPham,
                         Model model) {
        Pageable pageable = PageRequest.of(pageNo - 1, 9);
        model.addAttribute("selectedColors", new ArrayList<>());
        model.addAttribute("selectedNSX", new ArrayList<>());
        if (idMauSac != null) {
            model.addAttribute("selectedColors", idMauSac);
        } else {
            model.addAttribute("selectedColors", new ArrayList<>());
        }
        if (idNsx != null) {
            model.addAttribute("selectedNSX", idNsx);
        } else {
            model.addAttribute("selectedNSX", new ArrayList<>());
        }
        if(tenSearch != null){
            model.addAttribute("tenSearch", tenSearch);
        }
        if(idDongSanPham != null){
            model.addAttribute("idDongSanPham", idDongSanPham);
        } else {
            model.addAttribute("idDongSanPham", null);
        }

        //Lấy uri
        // Lấy tất cả các tham số từ URL
        Map<String, String[]> parameterMap = request.getParameterMap();

        // Tạo StringBuilder để xây dựng query string mới
        StringBuilder queryBuilder = new StringBuilder();
        boolean isFirst = true;

        // Duyệt qua tất cả tham số và chỉ giữ lại những tham số không phải pageNo
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String paramName = entry.getKey();
            String[] paramValues = entry.getValue();

            // Bỏ qua tham số pageNo
            if (!"pageNo".equals(paramName)) {
                for (String paramValue : paramValues) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        queryBuilder.append("&");
                    }
                    queryBuilder.append(paramName).append("=").append(paramValue);
                }
            }
        }
        model.addAttribute("isSearch", true);
        model.addAttribute("queryString", queryBuilder);

        model.addAttribute("selectedCategory", category);

        //Sản phẩm Lọc
        Page<SanPhamThongTinDTO> list = sanPhamService.getAllFillter(minPrice, maxPrice, idMauSac, idNsx, category, tenSearch, idDongSanPham, pageable);
        model.addAttribute("listSanPham", list.getContent());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("maxPage", list.getTotalPages());

        // Nếu người dùng không chọn, mặc định là toàn bộ range
        model.addAttribute("minPriceRange", sanPhamChiTietService.minPrice());
        model.addAttribute("maxPriceRange", sanPhamChiTietService.maxPrice());
        model.addAttribute("startMin", minPrice);
        model.addAttribute("startMax", maxPrice);
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listNSX", nsxService.getAll());
        model.addAttribute("listDongSanPham", dongSanPhamService.getAll());
        return "ViewKhachHang/SanPham";
    }

    @GetMapping("/chi-tiet-san-pham")
    public String chiTietSanPham(@RequestParam(required = false) Integer idSanPham,
                                 @RequestParam(required = false) Integer idMauSac,
                                 @RequestParam(required = false) Integer idKhoiLuong,
                                 Model model) {


        // Lấy thông tin sản phẩm
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
        SanPham sanPham = sanPhamService.getSanPhamById(idSanPham);
        List<SanPhamChiTiet> listSanPhamChiTiet = sanPhamChiTietService.getChiTietSabPhamByIdSanPham(idSanPham);
        List<MauSac> listMauSac = mauSacService.getMauSacByIdSanPhamChiTiet(sanPham.getId());
        List<KhoiLuong> listKhoiLuong = khoiLuongService.getKhoiLuongByIdSanPhamChiTiet(sanPham.getId());
        List<ChatLong> listChatLong = chatLongService.getChatLongByIdSanPhamChiTiet(sanPham.getId());

        // Kiểm tra nếu sản phẩm không tồn tại
        if (idMauSac == null || idKhoiLuong == null) {
            sanPhamChiTiet = listSanPhamChiTiet.get(0);
        }

        // Thêm dữ liệu vào model
        model.addAttribute("sanPham", sanPham);
        model.addAttribute("sanPhamChiTiet", sanPhamChiTiet);
        model.addAttribute("listAnhSanPham", anhSanPhamService.getAllByIdSanPham(idSanPham));
        model.addAttribute("listMauSac", listMauSac);
        model.addAttribute("listKhoiLuong", listKhoiLuong);
        model.addAttribute("listChatLong", listChatLong);
        if (sanPhamChiTiet.getTrangThai()) {
            model.addAttribute("trangThai", "Còn hàng");
        } else {
            model.addAttribute("trangThai", "Hết hàng");
        }

        return "ViewKhachHang/SanPhamChiTiet";
    }

}
