package com.example.webbanson.service;

import com.example.webbanson.dto.DoanhThuTheoDanhMuc;
import com.example.webbanson.dto.DoanhThuTheoKenh;
import com.example.webbanson.dto.KhachHangMuaNhieuNhat;
import com.example.webbanson.dto.SanPhamBanChayDto;
import com.example.webbanson.model.HoaDon;
import com.example.webbanson.repo.HoaDonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HoaDonService {
    private final HoaDonRepo repo;

    public Page<HoaDon> getAllHoaDon(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<HoaDon> getAllHoaDonOnline(Pageable pageable) {
        return repo.findAllByHinhThuc(true, pageable);
    }

    public Page<HoaDon> getAllHoaDonOfline(Pageable pageable) {
        return repo.findAllByHinhThuc(false, pageable);
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

    public Double doanhThuThang(LocalDate startDate, LocalDate endDate, Integer month) {
        if (startDate == null || endDate == null ) {
            if (month == null) {
                month = LocalDate.now().getMonthValue();
            }
            return repo.doanhThuThang(month) == null ? 0 : repo.doanhThuThang(month);
        }
        return repo.doanhThuKhoangNgay(startDate, endDate) == null ? 0 : repo.doanhThuKhoangNgay(startDate, endDate);
    }

    public Double doanhThuThangTruoc() {
        int currentMonth = LocalDate.now().getMonthValue() - 1;
        return repo.doanhThuThang(currentMonth) == null ? 0 : repo.doanhThuThang(currentMonth);
    }

    public Integer donHangAllThang(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            int currentMonth = LocalDate.now().getMonthValue();
            return repo.donHangAllThang(currentMonth) == null ? 0 : repo.donHangAllThang(currentMonth);
        }
        return repo.donHangAllKhoangNgay(startDate, endDate) == null ? 0 : repo.donHangAllKhoangNgay(startDate, endDate);
    }

    public Integer donHangThang(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            int currentMonth = LocalDate.now().getMonthValue();
            return repo.donHangThang(currentMonth) == null ? 0 : repo.donHangThang(currentMonth);
        }
        return repo.donHangThanhCongKhoangNgay(startDate, endDate) == null ? 0 : repo.donHangThanhCongKhoangNgay(startDate, endDate);
    }

    public Integer donHangHuyThang(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            int currentMonth = LocalDate.now().getMonthValue();
            return repo.donHangHuyThang(currentMonth) == null ? 0 : repo.donHangHuyThang(currentMonth);
        }
        return repo.donHangHuyKhoangNgay(startDate, endDate) == null ? 0 : repo.donHangHuyKhoangNgay(startDate, endDate);
    }

    public Integer donHangHoanThang(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            int currentMonth = LocalDate.now().getMonthValue();
            return repo.donHangHoanThang(currentMonth) == null ? 0 : repo.donHangHoanThang(currentMonth);
        }
        return repo.donHangHoanKhoangNgay(startDate, endDate) == null ? 0 : repo.donHangHoanKhoangNgay(startDate, endDate);
    }

    public Integer donHangThangTruoc() {
        int currentMonth = LocalDate.now().getMonthValue() - 1;
        return repo.donHangThang(currentMonth) == null ? 0 : repo.donHangThang(currentMonth);
    }

    public Integer soSanPhamThang(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            int currentMonth = LocalDate.now().getMonthValue();
            return repo.soSanPhamThang(currentMonth) == null ? 0 : repo.soSanPhamThang(currentMonth);
        }
        return repo.soSanPhamKhoangNgay(startDate, endDate) == null ? 0 : repo.soSanPhamKhoangNgay(startDate, endDate);
    }

    public Integer soSanPhamThangTruoc() {
        int currentMonth = LocalDate.now().getMonthValue() - 1;
        return repo.soSanPhamThang(currentMonth) == null ? 0 : repo.soSanPhamThang(currentMonth);
    }

    public List<SanPhamBanChayDto> getTopSanPhamBanChayTheoThang(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            int month = LocalDate.now().getMonthValue();
            return repo.topSanPhamBanChay(month, PageRequest.of(0, 5));
        }
        return repo.topSanPhamBanChayKhoangNgay(startDate, endDate, PageRequest.of(0, 5));
    }

    public List<DoanhThuTheoDanhMuc> DoanhThuTheoDanhMuc(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            int month = LocalDate.now().getMonthValue();
            return repo.DoanhThuTheoDanhMuc(month);
        }
        return repo.DoanhThuTheoDanhMucKhoangNgay(startDate, endDate);
    }

    public List<DoanhThuTheoKenh> DoanhThuTheoKenh(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            int month = LocalDate.now().getMonthValue();
            return repo.DoanhThuTheoKenh(month);
        }
        return repo.DoanhThuTheoKenhKhoangNgay(startDate, endDate);
    }

    public List<KhachHangMuaNhieuNhat> KhachHangMuaNhieuNhat(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            int month = LocalDate.now().getMonthValue();
            return repo.KhachHangMuaNhieuNhat(month);
        }
        return repo.KhachHangMuaNhieuNhatKhoangNgay(startDate, endDate);
    }

    public Integer KhachHangCu(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            int month = LocalDate.now().getMonthValue();
            return repo.countKhachHangCu(month);
        }
        return repo.countKhachHangCuKhoangNgay(startDate, endDate);
    }

    public Integer KhachHangMoi(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            int month = LocalDate.now().getMonthValue();
            return repo.countKhachHangMoi(month);
        }
        return repo.countKhachHangMoiKhoangNgay(startDate, endDate);
    }
}
