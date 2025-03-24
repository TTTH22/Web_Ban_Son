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
public class GioHangChiTietId implements java.io.Serializable {
    private static final long serialVersionUID = 3431587437200140371L;
    @NotNull
    @Column(name = "idGioHang", nullable = false)
    private Integer idGioHang;

    @NotNull
    @Column(name = "idSanPhamChiTiet", nullable = false)
    private Integer idSanPhamChiTiet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GioHangChiTietId entity = (GioHangChiTietId) o;
        return Objects.equals(this.idGioHang, entity.idGioHang) &&
                Objects.equals(this.idSanPhamChiTiet, entity.idSanPhamChiTiet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGioHang, idSanPhamChiTiet);
    }

}