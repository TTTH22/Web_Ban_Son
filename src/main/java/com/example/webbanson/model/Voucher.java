package com.example.webbanson.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "ma", length = 10, unique = true)
    private String ma;

    @NotBlank(message = "Tên không được để trống")
    @Size(max = 50, message = "Tên không được vượt quá 50 ký tự")
    @Nationalized
    @Column(name = "ten", length = 50)
    private String ten;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 255, message = "Mô tả không được vượt quá 255 ký tự")
    @Nationalized
    @Column(name = "moTa")
    private String moTa;

    @NotNull(message = "Hình thức giảm giá không được để trống")
    @Column(name = "hinhThucGiam")
    private Boolean hinhThucGiam;

    @NotNull(message = "Điều kiện không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Điều kiện phải lớn hơn 0")
    @Column(name = "dieuKien")
    private Double dieuKien;

    @NotNull(message = "Loại giảm giá không được để trống")
    @Column(name = "loaiGiam")
    private Boolean loaiGiam;

    @DecimalMin(value = "0.0", inclusive = false, message = "Giá trị giảm phải lớn hơn 0")
    @DecimalMax(value = "100.0", inclusive = false, message = "Giá trị giảm phải nhỏ hơn 100")
    @Column(name = "giaTriGiam")
    private Double giaTriGiam;

    @NotNull(message = "Giá trị giảm không được để trống")
    @DecimalMin(value = "0.0", message = "Giá trị giảm tối đa phải lớn hơn hoặc bằng 0")
    @Column(name = "giaTriGiamToiDa")
    private Double giaTriGiamToiDa;

    @Column(name = "ngayBatDau")
    private LocalDate ngayBatDau;

    @Override
    public String toString() {
        return "Voucher{" +
                "id=" + id +
                ", ma='" + ma + '\'' +
                ", ten='" + ten + '\'' +
                ", moTa='" + moTa + '\'' +
                ", hinhThucGiam=" + hinhThucGiam +
                ", dieuKien=" + dieuKien +
                ", loaiGiam=" + loaiGiam +
                ", giaTriGiam=" + giaTriGiam +
                ", giaTriGiamToiDa=" + giaTriGiamToiDa +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                ", trangThai=" + trangThai +
                '}';
    }

    @NotNull(message = "Ngày kết thúc không được để trống")
    @Future(message = "Ngày kết thúc phải sau ngày bắt đầu")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngayKetThuc")
    private LocalDate ngayKetThuc;

    @NotNull(message = "Trạng thái không được để trống")
    @Column(name = "trangThai")
    private Boolean trangThai;
}
