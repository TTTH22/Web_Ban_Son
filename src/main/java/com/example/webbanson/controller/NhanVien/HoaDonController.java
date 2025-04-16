package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.HoaDon;
import com.example.webbanson.model.NhanVien;
import com.example.webbanson.repo.HoaDonRepo;
import com.example.webbanson.service.HoaDonService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/nhan-vien/hoa-don")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HoaDonRepo hoaDonRepo;


    @GetMapping("")
    public String HoaDon(@RequestParam(defaultValue = "all") String loai,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "all") String payment,
                         @RequestParam(defaultValue = "") String keyword,
                         @RequestParam(defaultValue = "0") int status,
                         Model model, HttpSession session) {
        NhanVien nhanVien = (NhanVien) session.getAttribute("nhanVien");
        model.addAttribute("nhanVien", nhanVien);
        if(nhanVien.getChucVu().equals("Quản lý")) {
            model.addAttribute("checkLogin", true);
        }
        else {
            model.addAttribute("checkLogin", false);
        }
        int size = 7;
        Page<HoaDon> pageHoaDon;
        Pageable pageable = PageRequest.of(page, size, Sort.by("ngayTao").descending());
        // Tổng hóa đơn
        Integer totalHoaDon = hoaDonRepo.countAll();
        // Tổng hóa đơn tại quầy
        Integer totalHoaDonOffline = hoaDonRepo.countAllDonOfline();
        // Tổng hóa đơn online
        Integer totalHoaDonOnline = hoaDonRepo.countAllDonOnline();
        // Doanh thu
        Double doanhThu = hoaDonRepo.countDoanhThu();

        switch (loai) {
            case "offline":
                pageHoaDon = hoaDonService.getAllHoaDonOfline(pageable);
                break;
            case "online":
                pageHoaDon = hoaDonService.getAllHoaDonOnline(pageable);
                break;
            default:
                pageHoaDon = hoaDonService.getAllHoaDon(pageable);
                break;
        }
        if (!payment.equals("all") || status != 0 || !keyword.isEmpty()) {
            List<HoaDon> filteredList = pageHoaDon.getContent().stream()
                    .filter(hd -> payment.equals("all") || hd.getLoaiThanhToan().equalsIgnoreCase(payment))
                    .filter(hd -> status == 0 || hd.getTrangThai() == status)
                    .filter(hd -> keyword.isEmpty()
                            || hd.getMa().toLowerCase().contains(keyword.toLowerCase())
                            || (hd.getIdKhachHang().getTen() != null && hd.getIdKhachHang().getTen().toLowerCase().contains(keyword.toLowerCase())))
                    .collect(Collectors.toList());

            // phân trang lại bằng tay sau khi lọc
            int start = Math.min(page * size, filteredList.size());
            int end = Math.min(start + size, filteredList.size());

            List<HoaDon> pagedList = filteredList.subList(start, end);
            pageHoaDon = new PageImpl<>(pagedList, pageable, filteredList.size());
        }
        model.addAttribute("totalHoaDon", totalHoaDon);
        model.addAttribute("totalHoaDonOffline", totalHoaDonOffline);
        model.addAttribute("totalHoaDonOnline", totalHoaDonOnline);
        model.addAttribute("doanhThu", doanhThu);
        model.addAttribute("hoaDons", pageHoaDon.getContent());
        model.addAttribute("loai", loai);
        model.addAttribute("payment", payment);
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageHoaDon.getTotalPages());
        return "ViewNhanVien/HoaDon/HoaDon";
    }
}
