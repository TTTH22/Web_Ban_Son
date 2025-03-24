package com.example.webbanson.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class GiaoHang {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idHoaDon")
    private KhachHang idHoaDon;

    @Column(name = "ngayGiao")
    private LocalDate ngayGiao;

    @Column(name = "ngayNhan")
    private LocalDate ngayNhan;

    @Column(name = "trangThai")
    private Boolean trangThai;

}