package com.example.webbanson.repo;

import com.example.webbanson.model.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaChiRepo extends JpaRepository<DiaChi, Integer> {

    @Query(value = "SELECT d FROM DiaChi d WHERE d.idKhachHang.id = :id and d.trangThai = true")
    DiaChi getDiaChiNhanHangById(@Param("id") Integer id);
}
