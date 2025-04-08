package com.example.webbanson.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GioHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idSanPhamChiTiet", nullable = false)
    private SanPhamChiTiet idSanPhamChiTiet;

    @Column(name = "soLuong")
    private Integer soLuong;

    @Column(name = "donGia")
    private Double donGia;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idGioHang", nullable = false)
    private GioHang idGioHang;

    @Override
    public String toString() {
        return "GioHangChiTiet{" +
                "id=" + id +
                ", idSanPhamChiTiet=" + idSanPhamChiTiet +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", idGioHang=" + idGioHang +
                '}';
    }
}