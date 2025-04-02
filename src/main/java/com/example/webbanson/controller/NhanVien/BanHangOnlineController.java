package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.HoaDon;
import com.example.webbanson.model.Nsx;
import com.example.webbanson.service.DiaChiService;
import com.example.webbanson.service.HoaDonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/nhan-vien/ban-hang-online")
public class BanHangOnlineController {
    private final HoaDonService hoaDonService;
    private final DiaChiService diaChiService;

    @GetMapping("")
    public String BanHangOnline(@RequestParam(defaultValue = "1") Integer pageNo, Model model) {
        Page<HoaDon> list = hoaDonService.getAllHoaDonOnline(PageRequest.of(pageNo - 1, 7));
        model.addAttribute("listHoaDon", list.getContent());
        model.addAttribute("listDiaChi", diaChiService.getAll());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("max", list.getTotalPages());
        model.addAttribute("size", list.getTotalElements());
        model.addAttribute("successAdd", false);
        model.addAttribute("successUpdate", false);
        return "ViewNhanVien/BanHangOnline/BanHangOnline";
    }

     @GetMapping("/detail/{id}")
    public String detailBanHangOnline(@PathVariable Integer id,
                                    Model model) {
        model.addAttribute("banHang", hoaDonService.getOneBanHangOnlineById(id));
        Integer idKhchHang = hoaDonService.getOneBanHangOnlineById(id).getIdKhachHang().getId();
        model.addAttribute("diaChi", diaChiService.getDiaChiNhanHangById(idKhchHang));
        return "ViewNhanVien/BanHangOnline/DetailBanHangOnline";
    }
}
