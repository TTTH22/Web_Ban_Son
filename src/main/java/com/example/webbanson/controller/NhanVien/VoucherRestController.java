package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.KhachHang;
import com.example.webbanson.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nhan-vien/voucher")
public class VoucherRestController {

    @Autowired
    KhachHangService khachHangService;

    @PostMapping("/khach-hang/filter")
    @ResponseBody
    public List<KhachHang> filterKhachHang(@RequestBody Map<String, String> filter) {
        Integer rank = Integer.parseInt(filter.get("rank"));
        String spending = filter.get("spending");
        String time = filter.get("time");
        return khachHangService.filterKhachHang(rank, spending, time);
    }
}
