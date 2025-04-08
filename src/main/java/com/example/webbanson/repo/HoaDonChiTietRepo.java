package com.example.webbanson.repo;

import com.example.webbanson.model.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonChiTietRepo extends JpaRepository<HoaDonChiTiet, Integer> {
}
