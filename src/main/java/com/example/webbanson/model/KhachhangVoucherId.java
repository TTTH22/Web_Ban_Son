package com.example.webbanson.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class KhachhangVoucherId implements java.io.Serializable {
    private static final long serialVersionUID = 5084037249348367587L;
    @NotNull
    @Column(name = "idKhachHang", nullable = false)
    private Integer idKhachHang;

    @NotNull
    @Column(name = "idVoucher", nullable = false)
    private Integer idVoucher;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        KhachhangVoucherId entity = (KhachhangVoucherId) o;
        return Objects.equals(this.idVoucher, entity.idVoucher) &&
                Objects.equals(this.idKhachHang, entity.idKhachHang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVoucher, idKhachHang);
    }

}