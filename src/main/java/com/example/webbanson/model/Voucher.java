package com.example.webbanson.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Voucher {
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

    @Size(max = 255)
    @Nationalized
    @Column(name = "moTa")
    private String moTa;

    @Column(name = "dieuKien")
    private Double dieuKien;

    @Size(max = 50)
    @Nationalized
    @Column(name = "loaiGiam", length = 50)
    private String loaiGiam;

    @Column(name = "giaTriGiam")
    private Double giaTriGiam;

    @Column(name = "giaTriGiamToiDa")
    private Double giaTriGiamToiDa;

    @Column(name = "ngayBatDau")
    private LocalDate ngayBatDau;

    @Column(name = "ngayKetThuc")
    private LocalDate ngayKetThuc;

    @Column(name = "trangThai")
    private Boolean trangThai;

}