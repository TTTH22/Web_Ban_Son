package com.example.webbanson.service;

import com.example.webbanson.model.KhachHang;
import com.example.webbanson.model.Nsx;
import com.example.webbanson.repo.KhachHangRepo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KhachHangService {
    private final KhachHangRepo repo;

    public List<KhachHang> fillAll(){
        return repo.findAll();
    }

    public List<KhachHang> fillAllNoRank(){
        return repo.fillAllNoRank();
    }

    public List<KhachHang> fillAllRankBronze(){
        return repo.fillAllRankBronze();
    }

    public List<KhachHang> fillAllRankSiliver(){
        return repo.fillAllRankSiliver();
    }

    public List<KhachHang> fillAllRankGold(){
        return repo.fillAllRankGold();
    }

    public Page<KhachHang> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<KhachHang> searchKhachHang(String tenSearch, String sdtSearch, Pageable pageable) {
        return repo.searchKhachHang(tenSearch, sdtSearch, pageable);
    }

    public long countSearchKhachHang(String tenSearch, String sdtSearch, Pageable pageable) {
        return repo.searchKhachHang(tenSearch, sdtSearch, pageable).getTotalElements();
    }

    public KhachHang getOneKhachHangById(Integer id) {
        return repo.findById(id).get();
    }

    public KhachHang findByEmailAndMatKhau( String email, String matKhau) {
        Optional<KhachHang> khachHang = repo.findByEmailAndMatKhau(email, matKhau);
        return khachHang.orElse(null); // Trả về null nếu không tìm thấy
    }

    public List<KhachHang> filterKhachHang(Integer rank, String spending, String time) {
        LocalDate now = LocalDate.now();

        LocalDate newDate = null;
        LocalDate monthStart = null;
        LocalDate monthEnd = null;
        LocalDate quarterDate = null;

        if ("new".equals(time)) {
            newDate = now.minusDays(30);
        } else if ("month".equals(time)) {
            monthStart = now.minusDays(365);
            monthEnd = now.minusDays(30); // hoặc now để lấy đến hôm nay
        } else if ("quarter".equals(time)) {
            quarterDate = now.minusDays(365);
        }

        return repo.filterKhachHang(rank, spending, time, newDate, monthStart, monthEnd, quarterDate);
    }

    public void save(KhachHang khachHang) {
        repo.save(khachHang);
    }
}
