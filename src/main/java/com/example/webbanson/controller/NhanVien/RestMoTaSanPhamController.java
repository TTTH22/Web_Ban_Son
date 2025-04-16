package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.SanPham;
import com.example.webbanson.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/nhan-vien/mota-san-pham")
public class RestMoTaSanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @PostMapping("/luu-mo-ta")
    @ResponseBody
    public ResponseEntity<?> luuMoTa(@RequestBody Map<String, String> body) {
        String moTa = body.get("moTa");
        Integer idSanPham = Integer.valueOf(body.get("idSanPham"));
        SanPham sanPham = sanPhamService.getOneSanPhamById(idSanPham);
        sanPham.setMoTa(moTa);
        sanPhamService.add(sanPham);
        return ResponseEntity.ok(Map.of("success", true));
    }

}
