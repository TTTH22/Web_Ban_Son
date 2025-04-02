package com.example.webbanson.repo;

import com.example.webbanson.model.KhachHang;
import com.example.webbanson.model.Nsx;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepo extends JpaRepository<KhachHang, Integer> {

    public Page<KhachHang> findAll(Pageable pageable);

    @Query(value = "select kh from KhachHang kh where " +
            "(:tenSearch is null or kh.ten like %:tenSearch%) and " +
            "(:sdtSearch is null or kh.sdt like %:sdtSearch%)" ,
            countQuery = "select count(kh) from KhachHang kh where " +
                    "(:tenSearch is null or kh.ten like %:tenSearch%) and " +
                    "(:sdtSearch is null or kh.sdt like %:sdtSearch%)")
    Page<KhachHang> searchKhachHang(String tenSearch, String sdtSearch, Pageable pageable);
}
