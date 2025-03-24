package com.example.webbanson.dto;

import com.example.webbanson.model.SanPham;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SanPhamThongTinDTO {
    private SanPham sanPham;
    private Float donGia;
    private Float donGiaGiam;
    private Long tongSoLuongBan;
    private Float phanTramGiamGia;
}
