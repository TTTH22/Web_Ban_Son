package com.example.webbanson.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
public class DongSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50, message = "Tên dòng sản phẩm không được quá 50 ký tự")
    @NotBlank(message = "Tên dòng sản phẩm không được để trống")
    @Size(max = 50)
    @Nationalized
    @Column(name = "ten", length = 50)
    private String ten;

    @Size(max = 10)
    @Column(name = "ma", length = 10)
    private String ma;

    @Size(max = 255, message = "Mô tả không được quá 255 ký tự")
    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 255)
    @Nationalized
    @Column(name = "moTa")
    private String moTa;

    @NotNull(message = "Trạng thái không được để trống")
    @Column(name = "trangThai")
    private Boolean trangThai;

}