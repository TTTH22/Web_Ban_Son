package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.KhachHang;
import com.example.webbanson.model.KhachhangVoucher;
import com.example.webbanson.model.KhachhangVoucherId;
import com.example.webbanson.model.Voucher;
import com.example.webbanson.service.KhachHangService;
import com.example.webbanson.service.KhachHangVoucherService;
import com.example.webbanson.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nhan-vien/voucher")
public class VoucherRestController {

    @Autowired
    KhachHangService khachHangService;

    @Autowired
    VoucherService voucherService;

    @Autowired
    KhachHangVoucherService khachHangVoucherService;

    @PostMapping("/khach-hang/filter")
    @ResponseBody
    public List<KhachHang> filterKhachHang(@RequestBody Map<String, String> filter) {
        String rank = filter.get("rank");
        Integer rankId = null;
        System.out.println(rank);
        if (rank.equals("null")) {
            rankId = -1;
        } else {
            rankId = Integer.parseInt(rank);
        }
        String spending = filter.get("spending");
        String time = filter.get("time");
        List<KhachHang> list =  khachHangService.filterKhachHang(rankId, spending, time);
        if(list.size() == 0) System.out.println("Khong co khach hang nao");
        else System.out.println("Co khach hang");
        list.stream().forEach(s -> System.out.println(s.getId()));
        return khachHangService.filterKhachHang(rankId, spending, time);
    }

    @PostMapping("/gan-cho-khach-hang")
    @ResponseBody
    public ResponseEntity<?> ganVoucherChoKhachHang(@RequestBody Map<String, List<String>> request) {
        List<String> voucherIdsString = request.get("voucherIds");
        List<String> customerIdsString = request.get("customerIds");

        int countVoucherApDung = 0;
        int countVoucherKoApDung = 0;
        int countVoucherAll = 0;

        try {
            for (String voucherIdString : voucherIdsString) {
                Integer voucherId = Integer.parseInt(voucherIdString);
                Voucher voucher = voucherService.getOneVoucherById(voucherId);
                if (voucher == null) continue;

                for (String customerIdString : customerIdsString) {
                    Integer customerId = Integer.parseInt(customerIdString);
                    KhachHang khachHang = khachHangService.getOneKhachHangById(customerId);
                    if (khachHang == null) continue;

                    // Kiểm tra xem đã tồn tại chưa
                    countVoucherAll++;
                    boolean exists = khachHangVoucherService.existsByKhachHangIdAndVoucherId(khachHang, voucher);
                    if (exists) {
                        countVoucherKoApDung++;
                    } else {
                        KhachhangVoucherId id = new KhachhangVoucherId();
                        id.setIdKhachHang(customerId);
                        id.setIdVoucher(voucherId);
                        KhachhangVoucher kv = new KhachhangVoucher();
                        kv.setId(id);
                        kv.setIdKhachHang(khachHang);
                        kv.setIdVoucher(voucher);
                        kv.setTrangThai(true); // mặc định chưa sử dụng
                        khachHangVoucherService.save(kv);
                        countVoucherApDung++;
                    }
                }
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("countVoucherApDung", countVoucherApDung);
        response.put("countVoucherKoApDung", countVoucherKoApDung);
        response.put("countVoucherAll", countVoucherAll);

        return ResponseEntity.ok(response);
    }

}
