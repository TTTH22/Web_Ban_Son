package com.example.webbanson.repo;

import com.example.webbanson.model.KhachHang;
import com.example.webbanson.model.Nsx;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface KhachHangRepo extends JpaRepository<KhachHang, Integer> {

    Optional<KhachHang> findByEmailAndMatKhau(String email, String matKhau);

    public Page<KhachHang> findAll(Pageable pageable);

    @Query(value = "select kh from KhachHang kh where " +
            "(:tenSearch is null or kh.ten like %:tenSearch%) and " +
            "(:sdtSearch is null or kh.sdt like %:sdtSearch%)",
            countQuery = "select count(kh) from KhachHang kh where " +
                    "(:tenSearch is null or kh.ten like %:tenSearch%) and " +
                    "(:sdtSearch is null or kh.sdt like %:sdtSearch%)")
    Page<KhachHang> searchKhachHang(String tenSearch, String sdtSearch, Pageable pageable);

    @Query("select kh from KhachHang kh where kh.idRank is null ")
    public List<KhachHang> fillAllNoRank();

    @Query("select kh from KhachHang kh where kh.idRank.id = 1 ")
    public List<KhachHang> fillAllRankBronze();

    @Query("select kh from KhachHang kh where kh.idRank.id = 2 ")
    public List<KhachHang> fillAllRankSiliver();

    @Query("select kh from KhachHang kh where kh.idRank.id = 3 ")
    public List<KhachHang> fillAllRankGold();

    @Query("select kh from KhachHang kh where " +
            "((:rank = 0) or" +
            "(:rank is null or kh.idRank.id = :rank)) and " +
            "((:spending is null) or " +
            "(:spending = 'high' and kh.tongChiTieu >= 10000000) or " +
            "(:spending = 'medium' and (kh.tongChiTieu < 10000000 and kh.tongChiTieu < 5000000)) or " +
            "(:spending = 'medium' and kh.tongChiTieu < 5000000)) and " +
            "((:time IS NULL) OR" +
            "(:time = 'new' AND kh.ngayTao >= :newDate) OR " +
            "(:time = 'month' AND kh.ngayTao BETWEEN :monthStart AND :monthEnd) OR " +
            "(:time = 'quarter' AND kh.ngayTao < :quarterDate))")
    List<KhachHang> filterKhachHang(
            @Param("rank") Integer rank,
            @Param("spending") String spending,
            @Param("time") String time,
            @Param("newDate") Date newDate,
            @Param("monthStart") Date monthStart,
            @Param("monthEnd") Date monthEnd,
            @Param("quarterDate") Date quarterDate
    );
}
