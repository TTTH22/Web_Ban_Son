package com.example.webbanson.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class GioHangChiTiet {
    @EmbeddedId
    private GioHangChiTietId id;

    @MapsId("idGioHang")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idGioHang", nullable = false)
    private GioHang idGioHang;

    @MapsId("idSanPhamChiTiet")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idSanPhamChiTiet", nullable = false)
    private SanPhamChiTiet idSanPhamChiTiet;

    @Column(name = "soLuong")
    private Integer soLuong;

    @Column(name = "donGia")
    private Double donGia;

}