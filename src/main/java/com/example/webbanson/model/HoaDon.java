package com.example.webbanson.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class HoaDon {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 10)
    @Column(name = "ma", length = 10)
    private String ma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idNhanVien")
    private NhanVien idNhanVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idKhachHang")
    private KhachHang idKhachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idVoucher")
    private Voucher idVoucher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idVoucherPhiVanChuyen")
    private Voucher idVoucherPhiVanChuyen;

    @Column(name = "ngayTao")
    private LocalDate ngayTao;

    @Column(name = "tongTien", precision = 18, scale = 2)
    private BigDecimal tongTien;

    @Size(max = 250)
    @Nationalized
    @Column(name = "ghiChu", length = 250)
    private String ghiChu;

    @Size(max = 50)
    @Nationalized
    @Column(name = "loaiThanhToan", length = 50)
    private String loaiThanhToan;

    @Column(name = "hinhThuc")
    private Boolean hinhThuc;

    @Size(max = 50)
    @Nationalized
    @Column(name = "phiVanChuyen", length = 50)
    private String phiVanChuyen;

    @Column(name = "trangThai")
    private Boolean trangThai;

    @Column(name = "xacNhan")
    private Boolean xacNhan;

    @OneToMany(mappedBy = "idHoaDon")
    private List<HoaDonChiTiet> listHoaDonChiTiet;
}