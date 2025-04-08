package com.example.webbanson.controller.Login;

import com.example.webbanson.model.KhachHang;
import com.example.webbanson.service.KhachHangService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final KhachHangService khachHangService;

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(required = false) String redirect,
                        @RequestParam(required = false) String idMauSac,
                        @RequestParam(required = false) String idKhoiLuong,
                        @RequestParam(required = false) String soLuong) {
        StringBuilder url = new StringBuilder();
        if(redirect != null && idMauSac != null && idKhoiLuong != null && soLuong != null) {
             url.append(redirect).append("&idMauSac=").append(idMauSac).append("&idKhoiLuong=").append(idKhoiLuong).append("&soLuong=").append(soLuong);
        }
        else url = null;
        model.addAttribute("redirect", url);
        return "ViewKhachHang/Login";
    }

    @PostMapping("/login")
    public String loginSubmit(Model model,
                              @RequestParam(required = false) String redirect,
                              @RequestParam(required = true) String email,
                              @RequestParam(required = true) String matKhau,
                              HttpServletRequest request) {

        if (email.isBlank()) {
            model.addAttribute("emailError", "Email và mật khẩu không được để trống!");
            return "ViewKhachHang/Login"; // Giữ nguyên trang login
        }
        if (matKhau.isBlank()) {
            model.addAttribute("matKhauError", "Email và mật khẩu không được để trống!");
            return "ViewKhachHang/Login"; // Giữ nguyên trang login
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()) {
            model.addAttribute("emailError", "Email không đúng định dạng!");
            return "ViewKhachHang/Login";
        }

        // Kiểm tra đăng nhập bằng cách truy vấn DB
        KhachHang kh = khachHangService.findByEmailAndMatKhau(email, matKhau);

        if (kh != null) {
            // Lưu session
            HttpSession session = request.getSession();
            session.setAttribute("khachHang", kh);
            // Chuyển hướng sau khi đăng nhập thành công
            if (redirect != null && !redirect.isEmpty()) {
                return "redirect:" + redirect;
            }
            return "redirect:/trang-chu";
        }

        // Sai email hoặc mật khẩu
        model.addAttribute("error", "Email hoặc mật khẩu không đúng!");
        return "ViewKhachHang/Login";
    }
}

