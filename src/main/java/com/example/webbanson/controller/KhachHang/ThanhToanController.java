package com.example.webbanson.controller.KhachHang;

import com.example.webbanson.dto.DonHangRequest;
import com.example.webbanson.model.*;
import com.example.webbanson.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;


@RestController
public class ThanhToanController {
    @Autowired
    private DiaChiService diaChiService;

    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;


    @GetMapping("/tinh-khoang-cach")
    public ResponseEntity<?> tinhKhoangCach(@RequestParam String diaChi1, @RequestParam String diaChi2) throws IOException, InterruptedException {
        // B1: Geocode 2 địa chỉ sang tọa độ (sử dụng Nominatim)
        diaChi1 = removeDiacritics(diaChi1);
        diaChi2 = removeDiacritics(diaChi2);
        String toado1 = getToaDo(diaChi1);
        String toado2 = getToaDo(diaChi2);

        // B2: Gọi OSRM API để lấy khoảng cách
        Double khoangCachKm = getKhoangCachKm(toado1, toado2);
        if(khoangCachKm != null){
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "khoangCachKm", khoangCachKm // Trả về đơn giá mới
            ));
        }
        else{
            return ResponseEntity.badRequest().body(Map.of("success", false));
        }

    }

    private String getToaDo(String diaChi) throws IOException, InterruptedException {
        String encodedDiaChi = URLEncoder.encode(diaChi, StandardCharsets.UTF_8);
        String url = "https://nominatim.openstreetmap.org/search?q=" + encodedDiaChi + "&format=json&limit=1";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONArray results = new JSONArray(response.body());
        if (results.isEmpty()) throw new RuntimeException("Không tìm thấy tọa độ cho địa chỉ: " + diaChi);

        JSONObject obj = results.getJSONObject(0);
        return obj.getDouble("lon") + "," + obj.getDouble("lat"); // Định dạng cho OSRM là: lon,lat
    }

    private double getKhoangCachKm(String from, String to) throws IOException, InterruptedException {
        String url = "http://router.project-osrm.org/route/v1/driving/" + from + ";" + to + "?overview=false";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject json = new JSONObject(response.body());
        JSONArray routes = json.getJSONArray("routes");
        JSONObject route = routes.getJSONObject(0);

        return route.getDouble("distance") / 1000; // m -> km
    }

    public static String removeDiacritics(String input) {
        String normalized = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", ""); // Xóa dấu
    }

    @PatchMapping("/dia-chi/{id}/chon")
    public ResponseEntity<?> capNhatDiaChiDangChon(@PathVariable Integer id) {
        // Đặt tất cả địa chỉ về false trước
        diaChiService.boTrangThaiTatCa();

        // Sau đó set địa chỉ đang chọn thành true
        boolean daCapNhat = diaChiService.capNhatTrangThai(id, true);

        if (daCapNhat) {
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Không tìm thấy địa chỉ"));
        }
    }

    @PostMapping("/xac-nhan-don-hang")
    public ResponseEntity<?> datHang(@RequestBody Map<String, Object> request) {
        try {
            // Lấy idKhachHang
            Integer idKhachHang = Integer.parseInt(request.get("idKhachHang").toString());
            KhachHang khachHang = khachHangService.getOneKhachHangById(idKhachHang);
            DiaChi diaChi = diaChiService.getDiaChiNhanHangById(idKhachHang);

            // Lấy danh sách sản phẩm
            List<Map<String, Object>> productList = (List<Map<String, Object>>) request.get("products");
            List<DonHangRequest> products = new ArrayList<>();

            // Lấy các thông tin còn lại
            Voucher voucherProduct = null;
            Voucher voucherShip = null;
            String idVoucherProductString = request.get("voucherProduct") != null ? request.get("voucherProduct").toString() : null;
            if(idVoucherProductString != null){
                Integer idVoucherProduct = Integer.parseInt(idVoucherProductString);
                voucherProduct = voucherService.getOneVoucherById(idVoucherProduct);
            }
            else {
                voucherProduct = null;
            }
            String idVoucherShipString = request.get("voucherShip") != null ? request.get("voucherShip").toString() : null;
            if(idVoucherShipString != null){
                Integer idVoucherShip = Integer.parseInt(idVoucherShipString);
                voucherShip = voucherService.getOneVoucherById(idVoucherShip);
            }
            else {
                voucherShip = null;
            }
            Double total = Double.parseDouble(request.get("total").toString());
            Double shipFee = Double.parseDouble(request.get("shipFee").toString());
            String randomNumber = "HD" + String.format("%08d", new Random().nextInt(100000000));
            HoaDon hoaDon = new HoaDon(null, randomNumber, null, khachHang, diaChi, voucherProduct, voucherShip, LocalDate.now(), null, total, null, "Thanh toán khi nhận hàng", true, shipFee, 1, null, null, null, null, null, null, null);
            hoaDonService.save(hoaDon);

            Map<String, Object> response = new HashMap<>();
            response.put("susses", true);

            // Lưu thông tin chi tiết đơn hàng
            for (Map<String, Object> product : productList) {
                Integer idSanPhamChiTiet = Integer.parseInt(product.get("idSanPhamChiTiet").toString());
                SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietService.getOneSanPhamChiTietById(idSanPhamChiTiet);
                Integer soLuong = Integer.parseInt(product.get("soLuong").toString());

                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(null, hoaDon, sanPhamChiTiet, soLuong, (double) sanPhamChiTiet.getDonGiaGiam(), sanPhamChiTiet.getIdSanPham().getTen());
                hoaDonChiTietService.save(hoaDonChiTiet);
            }
            if(voucherProduct != null){
                voucherProduct.setTrangThai(false);
                voucherService.save(voucherProduct);
            }
            if(voucherShip != null){
                voucherShip.setTrangThai(false);
                voucherService.save(voucherShip);
            }

            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi đặt hàng");
        }
    }



}
