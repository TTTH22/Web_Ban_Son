package com.example.webbanson.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
public class DanhGia {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idKhachHang")
    private KhachHang idKhachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idHoaDon")
    private HoaDon idHoaDon;

    @Size(max = 500)
    @Nationalized
    @Column(name = "binhLuan", length = 500)
    private String binhLuan;

    @Column(name = "sao")
    private Integer sao;

    @Column(name = "trangThai")
    private Boolean trangThai;

}