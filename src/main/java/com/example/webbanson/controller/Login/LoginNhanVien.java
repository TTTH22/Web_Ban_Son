package com.example.webbanson.controller.Login;

import com.example.webbanson.model.NhanVien;
import com.example.webbanson.repo.NhanVienRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/nhan-vien")
public class LoginNhanVien {

    @Autowired
    private NhanVienRepo nhanVienRepo;

    @GetMapping("/login")
    public String login(Model model) {
        List<NhanVien> employees = nhanVienRepo.findAll(); // Giả sử bạn đã có dịch vụ lấy danh sách nhân viên
        model.addAttribute("employees", employees);
        return "ViewNhanVien/Login/LoginNhanVien";
    }
}
