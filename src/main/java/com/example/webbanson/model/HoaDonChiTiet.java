package com.example.webbanson.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonChiTiet {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idHoaDon")
    private HoaDon idHoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSanPhamChiTiet")
    private SanPhamChiTiet idSanPhamChiTiet;

    @Column(name = "soLuong")
    private Integer soLuong;

    @Column(name = "donGia")
    private Double donGia;

    public Double getTongTien() {
        if (soLuong != null && donGia != null) {
            return soLuong * donGia;
        }
        return 0.0;
    }

    @Column(name = "tenSanPham")
    @Nationalized
    private String tenSanPham;

}