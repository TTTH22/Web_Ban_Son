package com.example.webbanson.service;

import com.example.webbanson.model.HoaDon;
import com.example.webbanson.repo.HoaDonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void save(HoaDon hoaDon) {
        repo.save(hoaDon);
    }

    public List<HoaDon> getHoaDonChoXacNhan(Integer idKhachHang) {
        return repo.getHoaDonChoXacNhan(idKhachHang);
    }

    public List<HoaDon> listHoaDonDangGiao(Integer idKhachHang) {
        return repo.listHoaDonDangGiao(idKhachHang);
    }

    public List<HoaDon> listHoaDonDaGiao(Integer idKhachHang) {
        return repo.listHoaDonDaGiao(idKhachHang);
    }

    public List<HoaDon> listHoaDonChoHoan(Integer idKhachHang) {
        return repo.listHoaDonChoHoan(idKhachHang);
    }

    public List<HoaDon> listHoaDonDaHoan(Integer idKhachHang) {
        return repo.listHoaDonDaHoan(idKhachHang);
    }

    public List<HoaDon> listHoaDonDaHuy(Integer idKhachHang) {
        return repo.listHoaDonDaHuy(idKhachHang);
    }

    public List<HoaDon> getAllHoaDonChoXacNhan() {
        return repo.getAllHoaDonChoXacNhan();
    }

    public List<HoaDon> listAllHoaDonDangGiao() {
        return repo.listAllHoaDonDangGiao();
    }

    public List<HoaDon> listAllHoaDonDaGiao() {
        return repo.listAllHoaDonDaGiao();
    }

    public List<HoaDon> listAllHoaDonChoHoan() {
        return repo.listAllHoaDonChoHoan();
    }

    public List<HoaDon> listAllHoaDonDaHoan() {
        return repo.listAllHoaDonDaHoan();
    }

    public List<HoaDon> listAllHoaDonDaHuy() {
        return repo.listAllHoaDonDaHuy();
    }

}
