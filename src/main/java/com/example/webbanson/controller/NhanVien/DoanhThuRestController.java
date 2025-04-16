package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.dto.DoanhThuTheoDanhMuc;
import com.example.webbanson.dto.DoanhThuTheoKenh;
import com.example.webbanson.dto.SanPhamBanChayDto;
import com.example.webbanson.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DoanhThuRestController {
    @Autowired
    HoaDonService hoaDonService;

    @GetMapping("/nhan-vien/doanh-thu-theo-thang")
    @ResponseBody
    public List<Double> layDoanhThuTheoThang(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Double> doanhThuTungThang = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Double doanhThu = hoaDonService.doanhThuThang(null, null, i); // ví dụ: truyền tháng vào service
            doanhThuTungThang.add(doanhThu != null ? doanhThu : 0.0);
        }
        return doanhThuTungThang;
    }

    @GetMapping("/api/thong-ke/top-ban-chay")
    @ResponseBody
    public ResponseEntity<?> getTopSanPhamBanChayTrongThang(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<SanPhamBanChayDto> top5 = hoaDonService.getTopSanPhamBanChayTheoThang(startDate, endDate);


            // Giới hạn 5 sản phẩm
            List<SanPhamBanChayDto> top5Limited = top5.stream()
                    .limit(5)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(Map.of("data", top5Limited));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Đã xảy ra lỗi khi lấy dữ liệu"));
        }
    }

    @GetMapping("/api/thong-ke/doanh-thu-theo-danh-muc")
    @ResponseBody
    public ResponseEntity<?> getDoanhThuTheoDanhMuc(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<DoanhThuTheoDanhMuc> result = hoaDonService.DoanhThuTheoDanhMuc(startDate, endDate);

            return ResponseEntity.ok(Map.of("data", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Lỗi khi lấy doanh thu theo danh mục"));
        }
    }

    @GetMapping("/api/doanh-thu-theo-kenh")
    public ResponseEntity<Map<String, Object>> getDoanhThuTheoKenh(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DoanhThuTheoKenh> result = hoaDonService.DoanhThuTheoKenh(startDate, endDate);
        result.stream().forEach(s -> {
            if (s.getKenh().equals("1")) {
                s.setKenh("Online");
            }
            if (s.getKenh().equals("0")) {
                s.setKenh("Tại cửa hàng");
            }
        });

        return ResponseEntity.ok(Map.of("data", result));
    }

    @GetMapping("/api/thong-ke/khach-hang-theo-loai")
    public ResponseEntity<Map<String, Object>> thongKeKhachHangTheoLoai(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Integer moi = hoaDonService.KhachHangCu(startDate, endDate);
        Integer cu = hoaDonService.KhachHangMoi(startDate, endDate);

        Map<String, Object> result = new HashMap<>();
        result.put("moi", moi);
        result.put("cu", cu);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/thong-ke/trang-thai-don-hang")
    public ResponseEntity<?> getOrderStatusStats(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        Map<String, Object> result = Map.of(
                "thanhCong", hoaDonService.donHangThang(startDate, endDate),
                "biHuy", hoaDonService.donHangHuyThang(startDate, endDate),
                "biHoanTra", hoaDonService.donHangHoanThang(startDate, endDate)
        );
        return ResponseEntity.ok(Map.of("data", result));
    }

}
