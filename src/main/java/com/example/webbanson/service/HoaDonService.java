package com.example.webbanson.service;

import com.example.webbanson.model.HoaDon;
import com.example.webbanson.repo.HoaDonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HoaDonService {
    private final HoaDonRepo repo;

    public Page<HoaDon> getAllHoaDonOnline(Pageable pageable) {
        return repo.getAllHoaDonOnline(pageable);
    }

    public HoaDon getOneBanHangOnlineById(Integer id) {
        return repo.getOneBanHangOnlineById(id);
    }
}
