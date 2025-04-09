package com.example.webbanson.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class HoaDon {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name = "idDiaChi")
    private DiaChi idDiaChi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idVoucher")
    private Voucher idVoucher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idVoucherPhiVanChuyen")
    private Voucher idVoucherPhiVanChuyen;

    @Column(name = "ngayTao")
    private LocalDate ngayTao;

    @Column(name = "ngayNhan")
    private LocalDate ngayNhan;

    @Column(name = "tongTien")
    private Double tongTien;

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

    @Column(name = "phiVanChuyen")
    private Double phiVanChuyen;

    @Column(name = "trangThai")
    private Integer trangThai;

    @Size(max = 50)
    @Nationalized
    @Column(name = "lyDoHuy", length = 50)
    private String lyDoHuy;

    @Size(max = 50)
    @Nationalized
    @Column(name = "lyDoHoanHang", length = 50)
    private String lyDoHoanHang;

    @Size(max = 255)
    @Nationalized
    @Column(name = "ghiChuHoanHang", length = 255)
    private String ghiChuHoanHang;

    @Column(name = "danhGia")
    private Boolean danhGia;

    @Column(name = "ngayHuyHang")
    private LocalDate ngayHuyHang;

    @Column(name = "ngayHoanHang")
    private LocalDate ngayHoanHang;

    @OneToMany(mappedBy = "idHoaDon")
    private List<HoaDonChiTiet> listHoaDonChiTiet;
}