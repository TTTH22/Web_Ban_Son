package com.example.webbanson.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
public class KhoiLuong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự đ��ng tăng giá trị cho id m��i khi thêm mới sản phẩm
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 10)
    @Column(name = "ma", nullable = false, unique = true) // Đảm bảo mã sản phẩm là duy nhất
    private String ma;

    @NotNull(message = "Khối lượng không được để trống")
    @Min(value = 1, message = "Khối lượng phải lớn hơn 0")
    @Max(value = 500, message = "Khối lượng không được vượt quá 500g")
    @Column(name = "ten", nullable = false)
    private Integer ten;

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