package com.example.webbanson.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class SanPhamChiTiet {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 10)
    @Column(name = "ma", length = 10)
    private String ma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idSanPham")
    private SanPham idSanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMauSac")
    private MauSac idMauSac;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idChatLong")
    private ChatLong idChatLong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idKhoiLuong")
    private KhoiLuong idKhoiLuong;

    @Column(name = "soLuong")
    private Integer soLuong;

    @Column(name = "donGia")
    private float donGia;

    @Column(name = "donGiaGiam")
    private float donGiaGiam;


    @Size(max = 255)
    @Nationalized
    @Column(name = "moTa")
    private String moTa;

    @Column(name = "soLuongBan")
    private Integer soLuongBan;

    @Column(name = "soSaoDanhGia")
    private Double soSaoDanhGia;

    @Column(name = "trangThai")
    private Boolean trangThai;

    @Column(name = "ngayMoBan")
    private LocalDateTime ngayMoBan;

    public int getPhanTramGiamGia() {
        if (donGia == 0 || donGiaGiam == 0) {
            return 0;
        }
        float percent = (1 - (donGiaGiam / donGia)) * 100;
        return Math.round(percent);
    }
}