package com.example.webbanson.repo;

import com.example.webbanson.model.ChatLong;
import com.example.webbanson.model.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatLongRepo extends JpaRepository<ChatLong, Integer> {

    @Query("select spct.idChatLong from SanPhamChiTiet spct where spct.idSanPham.id = :id")
    List<ChatLong> getChatLongByIdSanPhamChiTiet(@Param("id") Integer id);
}
