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
@Table(name = "NSX")
public class Nsx {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50, message = "Tên nhà sản xuất không được quá 50 ký tự")
    @NotBlank(message = "Tên nhà sản xuất không được để trống")
    @Size(max = 50)
    @Nationalized
    @Column(name = "ten", length = 50)
    private String ten;

    @Size(max = 10)
    @Nationalized
    @Column(name = "ma", length = 10)
    private String ma;

    @Size(max = 255, message = "Mô tả không được quá 255 ký tự")
    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 255)
    @Nationalized
    @Column(name = "moTa", length = 255)
    private String moTa;

    @Size(max = 50, message = "Xuất xứ không được quá 50 ký tự")
    @NotBlank(message = "Xuất xứ không được để trống")
    @Size(max = 50)
    @Nationalized
    @Column(name = "xuatSu", length = 50)
    private String xuatSu;

    @NotNull(message = "Trạng thái không được để trống")
    @Column(name = "trangThai")
    private Boolean trangThai;

}