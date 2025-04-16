package com.example.webbanson.controller.NhanVien;

import com.example.webbanson.model.NhanVien;
import com.example.webbanson.repo.NhanVienRepo;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TongQuanController {

    @Autowired
    private NhanVienRepo nhanVienRepo;

    @GetMapping("/home")
    public String home(@RequestParam(required = false) Integer idNhanVien, HttpSession session, Model model) {
        if(idNhanVien != null) {
            NhanVien nhanVien = nhanVienRepo.findById(idNhanVien).orElse(null);
            session.setAttribute("nhanVien", nhanVien);
            model.addAttribute("nhanVien", nhanVien);
            if(nhanVien.getChucVu().equals("Quản lý")) {
                model.addAttribute("checkLogin", true);
            }
            else {
                model.addAttribute("checkLogin", false);
            }
        }
        return "ViewNhanVien/TongQuan/TongQuan";
    }
}
