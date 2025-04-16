package com.example.webbanson.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter({
        "/nhan-vien/*",
        "/nhan-vien",
        "/nhan-vien/logout",
        "/nhan-vien/reset-password"
})
public class NhanVienFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(request, response);
    }
}