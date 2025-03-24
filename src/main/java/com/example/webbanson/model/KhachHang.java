package com.example.webbanson.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class KhachHang {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 10)
    @Column(name = "ma", length = 10)
    private String ma;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ten", length = 50)
    private String ten;

    @Column(name = "ngaySinh")
    private LocalDate ngaySinh;

    @Size(max = 15)
    @Nationalized
    @Column(name = "sdt", length = 15)
    private String sdt;

    @Size(max = 100)
    @Nationalized
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 100)
    @Nationalized
    @Column(name = "matKhau", length = 100)
    private String matKhau;

    @Column(name = "gioiTinh")
    private Boolean gioiTinh;

    @Size(max = 255)
    @Nationalized
    @Column(name = "diaChi")
    private String diaChi;

    @Column(name = "tongChiTieu")
    private Integer tongChiTieu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRank")
    private Rank idRank;

    @Column(name = "trangThai")
    private Boolean trangThai;

}