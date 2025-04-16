package com.example.webbanson.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DanhGia {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idHoaDon")
    private HoaDon idHoaDon;

    @Size(max = 500)
    @Nationalized
    @Column(name = "binhLuan", length = 500)
    private String binhLuan;

    @Column(name = "sao")
    private Integer sao;

    @Column(name = "ngayTao")
    private LocalDate ngayTao;

    @Column(name = "trangThai")
    private Boolean trangThai;

}