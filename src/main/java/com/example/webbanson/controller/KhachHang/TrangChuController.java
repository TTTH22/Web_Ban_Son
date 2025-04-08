package com.example.webbanson.controller.KhachHang;

import com.example.webbanson.dto.GioHangChiTietDTO;
import com.example.webbanson.dto.SanPhamThongTinDTO;
import com.example.webbanson.model.*;
import com.example.webbanson.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class TrangChuController {

    private final SanPhamChiTietService sanPhamChiTietService;
    private final KhachHangService khachHangService;
    private final SanPhamService sanPhamService;
    private final MauSacService mauSacService;
    private final KhoiLuongService khoiLuongService;
    private final HoaDonService hoaDonService;
    private final NSXService nsxService;
    private final DongSanPhamService dongSanPhamService;
    private final AnhSanPhamService anhSanPhamService;
    private final DanhGiaService danhGiaService;
    private final GioHangChiTietService gioHangChiTietService;
    private final GioHangService gioHangService;
    private final DiaChiService diaChiService;
    private final VoucherService voucherService;

    private final HttpSession session;

    @GetMapping("/trang-chu")
    public String trangChu(Model model) {
//        KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
        KhachHang khachHang = khachHangService.getOneKhachHangById(2);
        if(khachHang == null){
            model.addAttribute("checkLogin", false);
        }
        else {
            List<GioHangChiTietDTO> list = new ArrayList<GioHangChiTietDTO>();
            model.addAttribute("checkLogin", true);
            model.addAttribute("khachHang", khachHang);
            GioHang gioHang = gioHangService.getGioHangByIdKhachHang(khachHang);
            List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietService.GioHangChiTietByIdKhachHang(gioHang.getId());
            for (GioHangChiTiet gioHangChiTiet : listGioHangChiTiet) {
                SanPham sanPham = gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham();
                List<MauSac> listMauSac = mauSacService.getMauSacByIdSanPhamChiTiet(sanPham.getId());
                List<KhoiLuong> listKhoiLuong = khoiLuongService.getKhoiLuongByMauSacAndSanPham
                        (gioHangChiTiet.getIdSanPhamChiTiet().getIdMauSac().getId(), sanPham.getId());
//                System.out.println(gioHangChiTiet.getIdSanPhamChiTiet().getIdMauSac().getId());
//                System.out.println(sanPham.getId());
                GioHangChiTietDTO gioHangChiTietDTO = new GioHangChiTietDTO();
                gioHangChiTietDTO.setId(gioHangChiTiet.getId());
                gioHangChiTietDTO.setListMauSac(listMauSac);
                gioHangChiTietDTO.setListKhoiLuong(listKhoiLuong);
                gioHangChiTietDTO.setGioHangChiTiet(gioHangChiTiet);
                list.add(gioHangChiTietDTO);
            }
            model.addAttribute("listGioHang", list);
            model.addAttribute("sizeGioHang", listGioHangChiTiet.size());
        }

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
        KhachHang khachHang = khachHangService.getOneKhachHangById(2);
        if(khachHang == null){
            model.addAttribute("checkLogin", false);
        }
        else {
            List<GioHangChiTietDTO> list = new ArrayList<GioHangChiTietDTO>();
            model.addAttribute("checkLogin", true);
            GioHang gioHang = gioHangService.getGioHangByIdKhachHang(khachHang);
            List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietService.GioHangChiTietByIdKhachHang(gioHang.getId());
            for (GioHangChiTiet gioHangChiTiet : listGioHangChiTiet) {
                SanPham sanPham = gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham();
                List<MauSac> listMauSac = mauSacService.getMauSacByIdSanPhamChiTiet(sanPham.getId());
                List<KhoiLuong> listKhoiLuong = khoiLuongService.getKhoiLuongByMauSacAndSanPham
                        (gioHangChiTiet.getIdSanPhamChiTiet().getIdMauSac().getId(), sanPham.getId());
//                System.out.println(gioHangChiTiet.getIdSanPhamChiTiet().getIdMauSac().getId());
//                System.out.println(sanPham.getId());
                GioHangChiTietDTO gioHangChiTietDTO = new GioHangChiTietDTO();
                gioHangChiTietDTO.setId(gioHangChiTiet.getId());
                gioHangChiTietDTO.setListMauSac(listMauSac);
                gioHangChiTietDTO.setListKhoiLuong(listKhoiLuong);
                gioHangChiTietDTO.setGioHangChiTiet(gioHangChiTiet);
                list.add(gioHangChiTietDTO);
            }
//            list.stream().forEach(gh -> System.out.println(gh.toString()));
            model.addAttribute("listGioHang", list);
            model.addAttribute("sizeGioHang", listGioHangChiTiet.size());
        }
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
        //        KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
        KhachHang khachHang = khachHangService.getOneKhachHangById(2);
        if(khachHang == null){
            model.addAttribute("checkLogin", false);
        }
        else {
            List<GioHangChiTietDTO> list = new ArrayList<GioHangChiTietDTO>();
            model.addAttribute("checkLogin", true);
            model.addAttribute("khachHang", khachHang);
            GioHang gioHang = gioHangService.getGioHangByIdKhachHang(khachHang);
            List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietService.GioHangChiTietByIdKhachHang(gioHang.getId());
            for (GioHangChiTiet gioHangChiTiet : listGioHangChiTiet) {
                SanPham sanPham = gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham();
                List<MauSac> listMauSac = mauSacService.getMauSacByIdSanPhamChiTiet(sanPham.getId());
                List<KhoiLuong> listKhoiLuong = khoiLuongService.getKhoiLuongByMauSacAndSanPham
                        (gioHangChiTiet.getIdSanPhamChiTiet().getIdMauSac().getId(), sanPham.getId());
//                System.out.println(gioHangChiTiet.getIdSanPhamChiTiet().getIdMauSac().getId());
//                System.out.println(sanPham.getId());
                GioHangChiTietDTO gioHangChiTietDTO = new GioHangChiTietDTO();
                gioHangChiTietDTO.setId(gioHangChiTiet.getId());
                gioHangChiTietDTO.setListMauSac(listMauSac);
                gioHangChiTietDTO.setListKhoiLuong(listKhoiLuong);
                gioHangChiTietDTO.setGioHangChiTiet(gioHangChiTiet);
                list.add(gioHangChiTietDTO);
            }
            model.addAttribute("listGioHang", list);
            model.addAttribute("sizeGioHang", listGioHangChiTiet.size());
        }
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
        if(minPrice == null){
            minPrice = sanPhamChiTietService.minPrice();
        }
        if(maxPrice == null){
            maxPrice = sanPhamChiTietService.maxPrice();
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
                                 @RequestParam(required = false) Integer soLuong,
                                 Model model) {


        //        KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
        KhachHang khachHang = khachHangService.getOneKhachHangById(2);
        if(khachHang == null){
            model.addAttribute("checkLogin", false);
        }
        else {
            List<GioHangChiTietDTO> list = new ArrayList<GioHangChiTietDTO>();
            model.addAttribute("checkLogin", true);
            model.addAttribute("khachHang", khachHang);
            GioHang gioHang = gioHangService.getGioHangByIdKhachHang(khachHang);
            List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietService.GioHangChiTietByIdKhachHang(gioHang.getId());
            for (GioHangChiTiet gioHangChiTiet : listGioHangChiTiet) {
                SanPham sanPham = gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham();
                List<MauSac> listMauSac = mauSacService.getMauSacByIdSanPhamChiTiet(sanPham.getId());
                List<KhoiLuong> listKhoiLuong = khoiLuongService.getKhoiLuongByMauSacAndSanPham
                        (gioHangChiTiet.getIdSanPhamChiTiet().getIdMauSac().getId(), sanPham.getId());
//                System.out.println(gioHangChiTiet.getIdSanPhamChiTiet().getIdMauSac().getId());
//                System.out.println(sanPham.getId());
                GioHangChiTietDTO gioHangChiTietDTO = new GioHangChiTietDTO();
                gioHangChiTietDTO.setId(gioHangChiTiet.getId());
                gioHangChiTietDTO.setListMauSac(listMauSac);
                gioHangChiTietDTO.setListKhoiLuong(listKhoiLuong);
                gioHangChiTietDTO.setGioHangChiTiet(gioHangChiTiet);
                list.add(gioHangChiTietDTO);
            }
            model.addAttribute("listGioHang", list);
            model.addAttribute("sizeGioHang", listGioHangChiTiet.size());
        }
        // Lấy thông tin sản phẩm
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
        SanPham sanPham = sanPhamService.getSanPhamById(idSanPham);
        List<SanPhamChiTiet> listSanPhamChiTiet = sanPhamChiTietService.getChiTietSabPhamByIdSanPhamIdMauSacIdKhoiLuong(idSanPham, idMauSac, idKhoiLuong);
        List<MauSac> listMauSac = mauSacService.getMauSacByIdSanPhamChiTiet(sanPham.getId());
        Double soSao = danhGiaService.getSoSaoBySanPham(idSanPham);

        if(soSao != null){
            Integer soNguoiDanhGia = danhGiaService.getSoNguoiDanhGia(idSanPham);
            model.addAttribute("checkSoSao", soSao);  // Số sao đã đánh giá
            int soSaoNguyen = soSao.intValue();  // Phần nguyên của số sao
            boolean coSaoLe = (soSao - soSaoNguyen) >= 0.5; // Kiểm tra có sao lẻ không
            model.addAttribute("soSaoNguyen", soSaoNguyen);
            model.addAttribute("coSaoLe", coSaoLe);
            model.addAttribute("soNguoiDanhGia", soNguoiDanhGia);
        }
        else model.addAttribute("checkSoSao", soSao);  // Số sao đã đánh giá

        // Kiểm tra nếu sản phẩm không tồn tại
        if (idMauSac == null && idKhoiLuong == null) {
            sanPhamChiTiet = listSanPhamChiTiet.get(0);
            MauSac mauSac = sanPhamChiTiet.getIdMauSac();
            List<KhoiLuong> listKhoiLuong = khoiLuongService.getKhoiLuongByIdSanPhamAndIDMauSac(sanPham.getId(), mauSac.getId());
            KhoiLuong khoiLuong = sanPhamChiTiet.getIdKhoiLuong();
            model.addAttribute("mauSac", mauSac);
            model.addAttribute("listKhoiLuong", listKhoiLuong);
            model.addAttribute("khoiLuong", khoiLuong);
        }
        if(idMauSac != null){
            if(idKhoiLuong == null){
                sanPhamChiTiet = listSanPhamChiTiet.get(0);
                MauSac mauSac = mauSacService.getOneMauSacById(idMauSac);
                List<KhoiLuong> listKhoiLuong = khoiLuongService.getKhoiLuongByIdSanPhamAndIDMauSac(sanPham.getId(), mauSac.getId());
                model.addAttribute("listKhoiLuong", listKhoiLuong);
                KhoiLuong khoiLuong = sanPhamChiTiet.getIdKhoiLuong();
                model.addAttribute("mauSac", mauSac);
                model.addAttribute("khoiLuong", khoiLuong);
            }
            else{
                sanPhamChiTiet = listSanPhamChiTiet.get(0);
                MauSac mauSac = mauSacService.getOneMauSacById(idMauSac);
                List<KhoiLuong> listKhoiLuong = khoiLuongService.getKhoiLuongByIdSanPhamAndIDMauSac(sanPham.getId(), mauSac.getId());
                model.addAttribute("listKhoiLuong", listKhoiLuong);
                KhoiLuong khoiLuong = khoiLuongService.getOneSanPhamById(idKhoiLuong);
                model.addAttribute("mauSac", mauSac);
                model.addAttribute("khoiLuong", khoiLuong);
            }
        }
        if(soLuong == null) model.addAttribute("soLuong", 1);

        // Thêm dữ liệu vào model
        model.addAttribute("sanPham", sanPham);
        model.addAttribute("sanPhamChiTiet", sanPhamChiTiet);
        model.addAttribute("listAnhSanPham", anhSanPhamService.getAllByIdSanPham(idSanPham));
        model.addAttribute("listMauSac", listMauSac);
        model.addAttribute("trangThai", sanPhamChiTiet.getTrangThai());


        return "ViewKhachHang/SanPhamChiTiet";
    }

    @GetMapping("/san-pham-chi-tiet/thanh-toan")
    public String thanhToan(@RequestParam("idSanPham") Integer idSanPham,
                            @RequestParam("idMauSac") Integer idMauSac,
                            @RequestParam("idKhachHang") Integer idKhachHang,
                            @RequestParam("soLuong") Integer soLuong,
                            @RequestParam("idKhoiLuong") Integer idKhoiLuong,
                            Model model) {
        List<SanPhamChiTiet> listSanPhamChiTiet = new ArrayList<>();
        KhachHang khachHang = khachHangService.getOneKhachHangById(idKhachHang);
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietService.getOneByMauSacAndKhoiLuong(idMauSac, idKhoiLuong, idSanPham);
        listSanPhamChiTiet.add(sanPhamChiTiet);
        List<DiaChi> listDiaChi = diaChiService.getAllDiaChiNhanHangById(idKhachHang);
        DiaChi diaChiMacDinh = diaChiService.getDiaChiNhanHangById(idKhachHang);
        model.addAttribute("diaChiMacDinh", diaChiService.getDiaChiNhanHangById(idKhachHang));
        model.addAttribute("listDiaChi", diaChiService.getAllDiaChiNhanHangById(idKhachHang));
        model.addAttribute("soLuong", soLuong);
        model.addAttribute("voucherSanPham", voucherService.listVocherSanPham(khachHang.getId()));
        model.addAttribute("voucherVanChuyen", voucherService.listVocherVanChuyen(khachHang.getId()));
        model.addAttribute("listSanPhamChiTiet", listSanPhamChiTiet);
        model.addAttribute("khachHang", khachHang);
        return "ViewKhachHang/ThanhToan";
    }

    @GetMapping("don-hang/{idKhachHang}")
    public String getDonHang(@PathVariable Integer idKhachHang, Model model) {
        List<HoaDon> listChoXacNhan = hoaDonService.getHoaDonChoXacNhan(idKhachHang);
        List<HoaDon> listDangGiao = hoaDonService.listHoaDonDangGiao(idKhachHang);
        List<HoaDon> listDaGiao = hoaDonService.listHoaDonDaGiao(idKhachHang);
        List<HoaDon> listChoHoan = hoaDonService.listHoaDonChoHoan(idKhachHang);
        List<HoaDon> listDaHoan = hoaDonService.listHoaDonDaHoan(idKhachHang);
        List<HoaDon> listDaHuy = hoaDonService.listHoaDonDaHuy(idKhachHang);
        model.addAttribute("listChoXacNhan", listChoXacNhan);
        model.addAttribute("listDangGiao", listDangGiao);
        model.addAttribute("listDaGiao", listDaGiao);
        model.addAttribute("listChoHoan", listChoHoan);
        model.addAttribute("listDaHoan", listDaHoan);
        model.addAttribute("listDaHuy", listDaHuy);
        return "ViewKhachHang/DonHang";
    }

    @GetMapping("/don-hang/mua-lai")
    public String getMuaLai(@RequestParam Integer idHoaDon,
                            Model model) {
        HoaDon hoaDon = hoaDonService.getOneBanHangOnlineById(idHoaDon);
        List<HoaDonChiTiet> listHoaDonChiTiet = hoaDon.getListHoaDonChiTiet();
        SanPhamChiTiet sanPhamChiTiet = listHoaDonChiTiet.get(0).getIdSanPhamChiTiet();
        Integer idSanPham = sanPhamChiTiet.getIdSanPham().getId();
        Integer idMauSac = sanPhamChiTiet.getIdMauSac().getId();
        Integer idKhoiLuong = sanPhamChiTiet.getIdKhoiLuong().getId();
        return "redirect:/chi-tiet-san-pham?idSanPham=" + idSanPham + "&idMauSac=" + idMauSac + "&idKhoiLuong=" + idKhoiLuong;
    }
}
