package com.example.webbanson.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class SanPhamChiTiet {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name = "idKhoiLuong")
    private KhoiLuong idKhoiLuong;

    @Min(value = 1, message = "Số lượng không được nhỏ hơn 1")
    @NotNull(message = "Số lượng không được để trống")
    @Column(name = "soLuong", nullable = false)
    private Integer soLuong;

    @DecimalMin(value = "0.0", inclusive = false, message = "Đơn giá phải lớn hơn 0")
    @Column(name = "donGia", nullable = false)
    private float donGia;

    @DecimalMin(value = "0.0", inclusive = false, message = "Đơn giá giảm không được âm")
    @Column(name = "donGiaGiam")
    private float donGiaGiam;

    @Size(max = 255, message = "Mô tả tối đa 255 ký tự")
    @NotBlank(message = "Mô tả không được để trống")
    @Nationalized
    @Column(name = "moTa")
    private String moTa;

    @Column(name = "soLuongBan")
    private Integer soLuongBan;

    @Column(name = "soSaoDanhGia")
    private Double soSaoDanhGia;

    @NotNull(message = "Trạng thái sản phẩm không được để trống")
    @Column(name = "trangThai")
    private Boolean trangThai;

    @NotNull(message = "Ngày mở bán không được để trống")
    @PastOrPresent(message = "Ngày mở bán không được ở trong tương lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngayMoBan")
    private LocalDate ngayMoBan;

    public int getPhanTramGiamGia() {
        if (donGia == 0 || donGiaGiam == 0) {
            return 0;
        }
        float percent = (1 - (donGiaGiam / donGia)) * 100;
        return Math.round(percent);
    }
}