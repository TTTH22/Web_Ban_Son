package com.example.webbanson.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 50, message = "Tên sản phẩm không được vượt quá 50 ký tự")
    @Nationalized
    @Column(name = "ten", length = 50)
    private String ten;

//    @NotBlank(message = "Mã sản phẩm không được để trống")
//    @Size(max = 10, message = "Mã sản phẩm không được vượt quá 10 ký tự")
    @Column(name = "ma", nullable = false, unique = true) // Đảm bảo mã sản phẩm là duy nhất
    private String ma;

    @Size(max = 255, message = "Mô tả không được vượt quá 255 ký tự")
    @NotBlank(message = "Mô tả không được để trống")
    @Nationalized
    @Column(name = "moTa")
    private String moTa;

//    @NotBlank(message = "Ảnh sản phẩm không được để trống")
    @Column(name = "AnhSanPham")
    private String anhSanPham;

    @NotNull(message = "Trạng thái sản phẩm không được để trống")
    @Column(name = "trangThai")
    private Boolean trangThai;

    @NotNull(message = "Nhà sản xuất không được để trống")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idNSX")
    private Nsx idNSX;

    @NotNull(message = "Dòng sản phẩm không được để trống")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDongSanPham")
    private DongSanPham idDongSanPham;

    @OneToMany(mappedBy = "idSanPham", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SanPhamChiTiet> sanPhamChiTietList;
}
