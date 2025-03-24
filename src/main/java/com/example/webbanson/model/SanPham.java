package com.example.webbanson.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Getter
@Setter
@Entity
public class SanPham {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ten", length = 50)
    private String ten;

    @Size(max = 255)
    @Nationalized
    @Column(name = "moTa")
    private String moTa;

    @Column(name = "AnhSanPham")
    private String anhSanPham;

    @Column(name = "trangThai")
    private Boolean trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idNSX")
    private Nsx idNSX;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDongSanPham")
    private DongSanPham idDongSanPham;

    @OneToMany(mappedBy = "idSanPham")
    private List<SanPhamChiTiet> sanPhamChiTietList;

}