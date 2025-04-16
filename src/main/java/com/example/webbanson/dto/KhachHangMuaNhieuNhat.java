package com.example.webbanson.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class KhachHangMuaNhieuNhat {
    String tenKhachHang;
    Object soLuongMua;
    Object tongTien;
    Object phanTramDoanhThu;
}
