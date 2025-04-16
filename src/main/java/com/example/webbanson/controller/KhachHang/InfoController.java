package com.example.webbanson.controller.KhachHang;

import com.example.webbanson.model.DiaChi;
import com.example.webbanson.model.KhachHang;
import com.example.webbanson.service.DiaChiService;
import com.example.webbanson.service.KhachHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private DiaChiService diaChiService;

    @GetMapping("/{idKhachHang}")
    public String info(@PathVariable("idKhachHang") Integer idKhachHang, Model model) {
        KhachHang khachHang = khachHangService.getOneKhachHangById(idKhachHang);
        model.addAttribute("khachHang", khachHang);
        List<DiaChi> listDiaChi = diaChiService.getAllDiaChiNhanHangById(idKhachHang);
        model.addAttribute("listDiaChi", listDiaChi);
        return "ViewKhachHang/Info";
    }

    @PostMapping("/update")
    public String updateThongTinKhachHang(@Valid @ModelAttribute KhachHang khachHang, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("khachHang", khachHang);
            return "ViewKhachHang/Info";
        }
        System.out.println("Hoàn");
        khachHangService.save(khachHang);
        redirectAttributes.addFlashAttribute("checkUpdate", true); // flash attribute cho redirect
        return "redirect:/info/" + khachHang.getId();
    }

}
