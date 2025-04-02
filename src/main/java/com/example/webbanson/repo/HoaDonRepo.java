package com.example.webbanson.repo;

import com.example.webbanson.model.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDon, Integer> {

    @Query(value = "SELECT h FROM HoaDon h WHERE h.hinhThuc = true",
    countQuery = "SELECT count(h) FROM HoaDon h WHERE h.hinhThuc = true")
    Page<HoaDon> getAllHoaDonOnline(Pageable pageable);

    @Query(value = "SELECT h FROM HoaDon h WHERE h.hinhThuc = true and h.id = :id")
    HoaDon getOneBanHangOnlineById(@Param("id") Integer id);
}
