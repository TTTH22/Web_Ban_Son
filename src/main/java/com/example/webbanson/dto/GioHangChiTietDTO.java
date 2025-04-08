package com.example.webbanson.dto;

import com.example.webbanson.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GioHangChiTietDTO {
    private Integer id;
    private GioHangChiTiet gioHangChiTiet;
    private List<MauSac> listMauSac;
    private List<KhoiLuong> listKhoiLuong;

    @Override
    public String toString() {
        return "GioHangChiTietDTO{" +
                "id=" + id +
                ", gioHangChiTiet=" + gioHangChiTiet +
                ", listMauSac=" + listMauSac +
                ", listKhoiLuong=" + listKhoiLuong +
                '}';
    }
}
