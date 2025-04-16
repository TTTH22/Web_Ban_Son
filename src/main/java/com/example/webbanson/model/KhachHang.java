package com.example.webbanson.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "ma", length = 10, unique = true)
    private String ma;

    @NotBlank(message = "Tên không được để trống")
    @Size(max = 50, message = "Tên tối đa 50 ký tự")
    @Nationalized
    @Column(name = "ten", length = 50)
    private String ten;

    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    @Column(name = "ngaySinh")
    private LocalDate ngaySinh;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(max = 15, message = "Số điện thoại tối đa 15 ký tự")
    @Pattern(regexp = "^(0[1-9][0-9]{8,9})$", message = "Số điện thoại không hợp lệ")
    @Nationalized
    @Column(name = "sdt", length = 15, unique = true)
    private String sdt;

    @NotBlank(message = "Email không được để trống")
    @Size(max = 100, message = "Email tối đa 100 ký tự")
    @Email(message = "Email không hợp lệ")
    @Nationalized
    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Nationalized
    @Column(name = "matKhau", length = 100)
    private String matKhau;

    @NotNull(message = "Giới tính không được để trống")
    @Column(name = "gioiTinh")
    private Boolean gioiTinh;

    @Size(max = 255, message = "Địa chỉ tối đa 255 ký tự")
    @Nationalized
    @Column(name = "diaChi")
    private String diaChi;

    @Min(value = 0, message = "Tổng chi tiêu không được âm")
    @Column(name = "tongChiTieu")
    private Double tongChiTieu;

    @Column(name = "ngaytao")
    private LocalDate ngayTao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRank")
    private Rank idRank;

    @Column(name = "trangThai")
    private Boolean trangThai;
}
