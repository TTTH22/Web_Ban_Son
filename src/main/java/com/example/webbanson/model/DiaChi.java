package com.example.webbanson.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
public class DiaChi {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idKhachHang")
    private KhachHang idKhachHang;

    @Size(max = 100)
    @Nationalized
    @Column(name = "tenNguoiNhan", length = 100)
    private String tenNguoiNhan;

    @Size(max = 15)
    @Column(name = "sdt", length = 15)
    private String sdt;

    @Size(max = 100)
    @Nationalized
    @Column(name = "diaChiCuThe", length = 100)
    private String diaChiCuThe;

    @Size(max = 50)
    @Nationalized
    @Column(name = "xa", length = 50)
    private String xa;

    @Size(max = 50)
    @Nationalized
    @Column(name = "huyen", length = 50)
    private String huyen;

    @Size(max = 50)
    @Nationalized
    @Column(name = "thanhPho", length = 50)
    private String thanhPho;

    @Column(name = "trangThai")
    private Boolean trangThai;

}