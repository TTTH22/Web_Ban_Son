package com.example.webbanson.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
public class Rank {
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

    @Column(name = "tongChiTieuDieuKien")
    private Double tongChiTieuDieuKien;

    @Column(name = "tongDonHangDieuKien")
    private Integer tongDonHangDieuKien;

    @Column(name = "mucGiam")
    private Double mucGiam;

    @Column(name = "mucGiamToiDa")
    private Double mucGiamToiDa;

    @Column(name = "trangThai")
    private Boolean trangThai;

}