package com.example.webbanson.service;

import com.example.webbanson.dto.SanPhamThongTinDTO;
import com.example.webbanson.model.SanPham;
import com.example.webbanson.model.SanPhamChiTiet;
import com.example.webbanson.repo.SanPhamRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SanPhamService {
    private final SanPhamRepo repo;

    public List<SanPham> getAll() {
        return repo.findAll();
    }

    public List<SanPham> getAllConSuDung() {
        return repo.getAllConSuDung();
    }

    public List<SanPham> getAllDesc() {
        return repo.findSanPhamByIdDesc();
    }

    public Page<SanPham> getAllPageSanPham(Pageable pageable) {
        return repo.findAllByIdDesc(pageable);
    }

    public Page<SanPhamThongTinDTO> getAllPage(Pageable pageable) {
        List<Object[]> result = repo.listSanPham(pageable);
        List<SanPhamThongTinDTO> dtoList = new ArrayList<>();
        for (Object[] row : result) {
            SanPham sanPham = (SanPham) row[0];
            Float donGiaGiamNhoNhat = (Float) row[1];
            Long tongSoLuongBan = ((Number) row[2]).longValue();;

            Float giaGoc = sanPham.getSanPhamChiTietList()
                    .stream()
                    .map(SanPhamChiTiet::getDonGia)
                    .min(Float::compareTo)
                    .orElse(0f); // Lấy giá gốc nhỏ nhất

            // Tính phần trăm giảm giá
            float phanTram = 0;
            if (giaGoc != null && giaGoc != 0 && donGiaGiamNhoNhat != null && donGiaGiamNhoNhat != 0) {
                phanTram = Math.round(((giaGoc - donGiaGiamNhoNhat) * 100) / giaGoc);
            }

            SanPhamThongTinDTO dto = new SanPhamThongTinDTO();
            dto.setSanPham(sanPham);
            dto.setDonGiaGiam(donGiaGiamNhoNhat);
            dto.setDonGia(giaGoc);
            dto.setTongSoLuongBan(tongSoLuongBan);
            dto.setPhanTramGiamGia(phanTram);

            dtoList.add(dto);
        }
        long totalElements = repo.countSanPham(); // Tổng số bản ghi (bạn cần tạo method đếm!)
        Page<SanPhamThongTinDTO> page = new PageImpl<>(dtoList, pageable, totalElements);

        return page;

    }

    public List<SanPhamThongTinDTO> getTop12SanPhamBanChay(Pageable pageable){
        List<Object[]> result = repo.listSanPhamBanChay(pageable);

        List<SanPhamThongTinDTO> dtoList = new ArrayList<>();

        for (Object[] row : result) {
            SanPham sanPham = (SanPham) row[0];
            Float donGiaGiamNhoNhat = (Float) row[1];
            Long tongSoLuongBan = ((Number) row[2]).longValue();;

            Float giaGoc = sanPham.getSanPhamChiTietList()
                    .stream()
                    .map(SanPhamChiTiet::getDonGia)
                    .min(Float::compareTo)
                    .orElse(0f); // Lấy giá gốc nhỏ nhất

            // Tính phần trăm giảm giá
            float phanTram = 0;
            if (giaGoc != null && giaGoc != 0 && donGiaGiamNhoNhat != null && donGiaGiamNhoNhat != 0) {
                phanTram = Math.round(((giaGoc - donGiaGiamNhoNhat) * 100) / giaGoc);
            }

            SanPhamThongTinDTO dto = new SanPhamThongTinDTO();
            dto.setSanPham(sanPham);
            dto.setDonGiaGiam(donGiaGiamNhoNhat);
            dto.setDonGia(giaGoc);
            dto.setTongSoLuongBan(tongSoLuongBan);
            dto.setPhanTramGiamGia(phanTram);

            dtoList.add(dto);
        }

        return dtoList;
    }

    public List<SanPhamThongTinDTO> getTop16SanPhamGiamGia(Pageable pageable){
        List<Object[]> result = repo.listSanPhamGiamGia(pageable);
        List<SanPhamThongTinDTO> dtoList = new ArrayList<>();

        for (Object[] row : result) {
            SanPhamThongTinDTO dto = new SanPhamThongTinDTO();

            // 1. Fix kiểu dữ liệu ID:
            Long sanPhamId = ((Number) row[0]).longValue();
            Optional<SanPham> optionalSanPham = repo.findById(sanPhamId.intValue());
            if (optionalSanPham.isEmpty()) continue;
            SanPham sanPham = optionalSanPham.get();

            // 2. Fix kiểu dữ liệu % giảm giá:
            Float phanTramGiamGiaLonNhat = ((Number) row[1]).floatValue();

            // 3. Tổng số lượng bán:
            Long tongSoLuongBan = ((Number) row[2]).longValue();

            Float giamLonNhat = Float.MAX_VALUE; // Sửa lại cho đúng

            for (SanPhamChiTiet ct : sanPham.getSanPhamChiTietList()) {
                Float goc = ct.getDonGia();
                Float giam = ct.getDonGiaGiam();

                // 4. Tính phần trăm giảm
                float tinhToanGiam = 100 - giam / goc * 100;

                // 5. So sánh chính xác hơn:
                if (Math.abs(tinhToanGiam - phanTramGiamGiaLonNhat) < 0.001 && giam < giamLonNhat) {
                    dto.setDonGiaGiam(giam);
                    dto.setDonGia(goc);
                    giamLonNhat = giam;
                }
            }

            dto.setSanPham(sanPham);
            dto.setTongSoLuongBan(tongSoLuongBan);
            dto.setPhanTramGiamGia(phanTramGiamGiaLonNhat);

            dtoList.add(dto);
        }

        return dtoList;
    }

    public Page<SanPhamThongTinDTO> getAllFillter(Float minPrice, Float maxPrice, List<Integer> idMauSac, List<Integer> idNsx,
                                                  Integer category, String tenSearch, Integer idDongSanPham, Pageable pageable) {
        Page<Object[]> result = null;
        if(category == 1){
            result = repo.searchSanPhamCategory1(minPrice, maxPrice, idMauSac, idNsx, idDongSanPham, tenSearch, category, pageable);
        } else if (category == 2) {
            result = repo.searchSanPhamCategory2(minPrice, maxPrice, idMauSac, idNsx, idDongSanPham, tenSearch, category, pageable);
        }
        else if (category == 3) {
            result = repo.searchSanPhamCategory3(minPrice, maxPrice, idMauSac, idNsx, idDongSanPham, tenSearch, category, pageable);
        }
        else if (category == 4) {
            result = repo.searchSanPhamCategory4(minPrice, maxPrice, idMauSac, idNsx, idDongSanPham, tenSearch, category, pageable);
        }
        else if (category == 5) {
            result = repo.searchSanPhamCategory5(minPrice, maxPrice, idMauSac, idNsx, idDongSanPham, tenSearch, category, pageable);
        }

        List<SanPhamThongTinDTO> dtoList = new ArrayList<>();
        for (Object[] row : result.getContent()) {
            SanPham sanPham = (SanPham) row[0];
            Float donGiaGiamNhoNhat = (Float) row[1];
            Long tongSoLuongBan = ((Number) row[2]).longValue();;

            Float giaGoc = sanPham.getSanPhamChiTietList()
                    .stream()
                    .map(SanPhamChiTiet::getDonGia)
                    .min(Float::compareTo)
                    .orElse(0f); // Lấy giá gốc nhỏ nhất

            // Tính phần trăm giảm giá
            float phanTram = 0;
            if (giaGoc != null && giaGoc != 0 && donGiaGiamNhoNhat != null && donGiaGiamNhoNhat != 0) {
                phanTram = Math.round(((giaGoc - donGiaGiamNhoNhat) * 100) / giaGoc);
            }

            SanPhamThongTinDTO dto = new SanPhamThongTinDTO();
            dto.setSanPham(sanPham);
            dto.setDonGiaGiam(donGiaGiamNhoNhat);
            dto.setDonGia(giaGoc);
            dto.setTongSoLuongBan(tongSoLuongBan);
            dto.setPhanTramGiamGia(phanTram);

            dtoList.add(dto);
        }
        Page<SanPhamThongTinDTO> page = new PageImpl<>(dtoList, pageable, result.getTotalElements());

        return page;
    }

    public SanPham getSanPhamById(Integer id) {
        return repo.findById(id).get();
    }

    public Page<SanPham> searchSanPham( String tenSearch ,Integer idNSX, Integer idDongSanPham, Pageable pageable) {
        return repo.searchSanPham(tenSearch ,idNSX, idDongSanPham, pageable);
    }

    public Long conntSearchSanPham( String tenSearch ,Integer idNSX, Integer idDongSanPham, Pageable pageable) {
        Page<SanPham> pageResult = repo.searchSanPham(tenSearch ,idNSX, idDongSanPham, pageable);
        return pageResult.getTotalElements();
    }

    public void add(SanPham sanPham) {
        repo.save(sanPham);
    }
    
    public SanPham getOneSanPhamById(Integer id) {
        return repo.findById(id).get();
    }

    public void update(SanPham sanPham) {
        repo.save(sanPham);
    }
}
