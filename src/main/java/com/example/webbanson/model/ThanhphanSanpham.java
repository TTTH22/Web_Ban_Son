package com.example.webbanson.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ThanhPhan_SanPham")
public class ThanhphanSanpham {
    @EmbeddedId
    private ThanhphanSanphamId id;

    @MapsId("idThanhPhan")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idThanhPhan", nullable = false)
    private ThanhPhan idThanhPhan;

    @MapsId("idSanPham")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idSanPham", nullable = false)
    private SanPham idSanPham;

}