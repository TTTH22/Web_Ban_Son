package com.example.webbanson.dto;

import com.example.webbanson.model.SanPhamChiTiet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DonHangRequest {
    SanPhamChiTiet sanPhamChiTiet;
    Integer soLuong;
}
