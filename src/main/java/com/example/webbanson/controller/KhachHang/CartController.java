package com.example.webbanson.controller.KhachHang;

import com.example.webbanson.model.*;

import com.example.webbanson.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private GioHangChiTietService gioHangChiTietService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private KhoiLuongService khoiLuongService;

    @Autowired
    private MauSacService mauSacService;

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;


    @PostMapping("/updateQuantity")
    public ResponseEntity<?> updateQuantity(@RequestBody Map<String, Integer> requestData) {
        Integer gioHangChiTietId = requestData.get("id");
        Integer quantityChange = requestData.get("quantityChange");

        GioHangChiTiet gioHangChiTiet = gioHangChiTietService.getById(gioHangChiTietId);
        if (gioHangChiTiet != null) {
            int newQuantity = gioHangChiTiet.getSoLuong() + quantityChange;
            if (newQuantity > 0) {
                gioHangChiTiet.setSoLuong(newQuantity);
                gioHangChiTietService.save(gioHangChiTiet);
            } else {
                gioHangChiTietService.delete(gioHangChiTietId);
            }
            return ResponseEntity.ok(Map.of("success", true));
        }
        return ResponseEntity.badRequest().body(Map.of("success", false));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeFromCart(@PathVariable Integer id) {
        gioHangChiTietService.delete(id);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @RequestMapping(value = "/getKhoiLuongByMauSac", method = RequestMethod.GET)
    @ResponseBody
    public List<KhoiLuong> getKhoiLuongByMauSac(@RequestParam("idMauSac") Integer idMauSac, @RequestParam("idSanPham") Integer idSanPham) {
        // Lấy danh sách KhoiLuong từ dịch vụ dựa trên idMauSac
        List<KhoiLuong> khoiLuongList = khoiLuongService.getKhoiLuongByMauSacAndSanPham(idMauSac, idSanPham);
        return khoiLuongList;
    }

    @PostMapping("/updateMauSac")
    public ResponseEntity<?> updateMauSac(@RequestBody Map<String, Integer> requestData) {
        Integer gioHangChiTietId = requestData.get("id");
        Integer newMauSacId = requestData.get("mauSacId");

        // Lấy chi tiết sản phẩm trong giỏ hàng
        GioHangChiTiet gioHangChiTiet = gioHangChiTietService.getById(gioHangChiTietId);
        SanPham sanPham = gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham();
        if (gioHangChiTiet != null) {
            // Cập nhật lại màu sắc và khối lượng của sản phẩm
            MauSac mauSac = mauSacService.getById(newMauSacId);
            List<KhoiLuong> listKhoiLuong = khoiLuongService.getKhoiLuongByIdSanPhamAndIDMauSac(sanPham.getId(), mauSac.getId());
            KhoiLuong khoiLuong = listKhoiLuong.get(0); // Lấy khối lượng đầu tiên từ danh sách lấy ra

            if (mauSac != null && khoiLuong != null) {
                SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietService.getOneByMauSacAndKhoiLuong(newMauSacId, khoiLuong.getId(), sanPham.getId());
                System.out.println(sanPhamChiTiet);
                gioHangChiTiet.setIdSanPhamChiTiet(sanPhamChiTiet); //
                gioHangChiTietService.save(gioHangChiTiet);
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "donGiaMoi", sanPhamChiTiet.getDonGiaGiam() // Trả về đơn giá mới
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Màu sắc hoặc khối lượng không hợp lệ"));
            }
        }

        // Trả về phản hồi lỗi nếu không tìm thấy chi tiết giỏ hàng
        return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Sản phẩm không tồn tại"));
    }

    @PostMapping("/updateKhoiLuong")
    public ResponseEntity<?> updateKhoiLuong(@RequestBody Map<String, Integer> requestData) {
        Integer gioHangChiTietId = requestData.get("id");
        Integer newMauSacId = requestData.get("mauSacId");
        Integer newKhoiLuongId = requestData.get("khoiLuongId");

        // Lấy chi tiết sản phẩm trong giỏ hàng
        GioHangChiTiet gioHangChiTiet = gioHangChiTietService.getById(gioHangChiTietId);
        SanPham sanPham = gioHangChiTiet.getIdSanPhamChiTiet().getIdSanPham();
        if (gioHangChiTiet != null) {
            // Cập nhật lại màu sắc và khối lượng của sản phẩm
            MauSac mauSac = mauSacService.getById(newMauSacId);
            KhoiLuong khoiLuong = khoiLuongService.getOneSanPhamById(newKhoiLuongId);

            if (mauSac != null && khoiLuong != null) {
                SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietService.getOneByMauSacAndKhoiLuong(newMauSacId, newKhoiLuongId, sanPham.getId());
                System.out.println(sanPhamChiTiet);
                gioHangChiTiet.setIdSanPhamChiTiet(sanPhamChiTiet); //
                gioHangChiTietService.save(gioHangChiTiet);
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "donGiaMoi", sanPhamChiTiet.getDonGiaGiam() // Trả về đơn giá mới
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Màu sắc hoặc khối lượng không hợp lệ"));
            }
        }

        // Trả về phản hồi lỗi nếu không tìm thấy chi tiết giỏ hàng
        return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Sản phẩm không tồn tại"));
    }

    @PostMapping("/calculateTotal")
    public ResponseEntity<?> calculateTotal(@RequestBody Integer idGioHang) {
        // Lấy danh sách chi tiết giỏ hàng từ service
        List<GioHangChiTiet> list = gioHangChiTietService.GioHangChiTietByIdKhachHang(idGioHang);
        System.out.println(idGioHang);
        System.out.println(list.size());
        // Tính tổng tiền
        AtomicReference<Double> sum = new AtomicReference<>(0.0);
        list.forEach(gh -> {
            // Tính toán tổng giá trị
            double donGiaGiam = gh.getIdSanPhamChiTiet().getDonGiaGiam();
            int soLuong = gh.getSoLuong();

            // Cập nhật tổng tiền sau khi tính toán
            sum.updateAndGet(currentSum -> currentSum + (soLuong * donGiaGiam));
        });
        System.out.println(sum);
        // Trả về tổng tiền
        return ResponseEntity.ok(Map.of(
                "tongTien", sum
        ));
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Integer> requestData) {
        Integer idSanPham = requestData.get("idSanPham");
        Integer idMauSac = requestData.get("idMauSac");
        Integer idKhoiLuong = requestData.get("idKhoiLuong");
        Integer idKhachHang = requestData.get("idKhachHang");
        Integer soLuong = requestData.get("soLuong");
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietService.getOneByMauSacAndKhoiLuong(idMauSac, idKhoiLuong, idSanPham);
        KhachHang khachHang = khachHangService.getOneKhachHangById(idKhachHang);
        GioHang gioHang = gioHangService.getGioHangByIdKhachHang(khachHang);
        if(sanPhamChiTiet != null && khachHang != null && gioHang != null){
            gioHangChiTietService.save(new GioHangChiTiet(null, sanPhamChiTiet, soLuong, (double) sanPhamChiTiet.getDonGiaGiam(), gioHang));
            return ResponseEntity.ok(Map.of("success", true));
        }
        else return ResponseEntity.badRequest().body(Map.of("success", false));
    }

}
