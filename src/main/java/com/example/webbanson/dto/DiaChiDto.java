package com.example.webbanson.dto;

import com.example.webbanson.model.KhachHang;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DiaChiDto {
    private KhachHang idKhachHang;

    private String tenNguoiNhan;

    private String sdt;

    private String diaChiCuThe;

    private String xa;

    private String huyen;

    private String thanhPho;
}
