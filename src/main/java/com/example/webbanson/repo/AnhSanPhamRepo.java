package com.example.webbanson.repo;

import com.example.webbanson.model.SanPhamAnh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnhSanPhamRepo extends JpaRepository<SanPhamAnh, Integer> {

    @Query("select anh from SanPhamAnh anh where anh.idSanPham.id = :id")
    List<SanPhamAnh> getAnhByIdSanPhamAnh(@Param("id") Integer id);
}
