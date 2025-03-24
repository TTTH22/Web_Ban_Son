package com.example.webbanson.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "KhachHang_Voucher")
public class KhachhangVoucher {
    @EmbeddedId
    private KhachhangVoucherId id;

    @MapsId("idKhachHang")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idKhachHang", nullable = false)
    private KhachHang idKhachHang;

    @MapsId("idVoucher")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idVoucher", nullable = false)
    private Voucher idVoucher;

    @Column(name = "trangThai")
    private Boolean trangThai;

}