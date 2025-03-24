package com.example.webbanson.service;

import com.example.webbanson.model.ChatLong;
import com.example.webbanson.model.KhoiLuong;
import com.example.webbanson.repo.ChatLongRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatLongService {
    private final ChatLongRepo repo;

    public List<ChatLong> getChatLongByIdSanPhamChiTiet(Integer id) {
        return repo.getChatLongByIdSanPhamChiTiet(id); //;
    }
}
