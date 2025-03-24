package com.example.webbanson.repo;

import com.example.webbanson.model.DongSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DongSanPhamRepo extends JpaRepository<DongSanPham, Integer> {
}
