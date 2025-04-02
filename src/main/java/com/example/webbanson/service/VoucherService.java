package com.example.webbanson.service;

import com.example.webbanson.model.Voucher;
import com.example.webbanson.repo.VoucherRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepo repo;

    public List<Voucher> getAll(){
        return repo.findAll();
    }

    public Page<Voucher> findAllIdDesc(Pageable pageable){
        return repo.findAllIdDesc(pageable);
    }

    public Page<Voucher> searchVoucher(String tenSearch, Integer hinhThucSearch, Integer loaiGiamSearch, Integer trangThaiSearch, Pageable pageable ) {
        return repo.searchVoucher(tenSearch, hinhThucSearch, loaiGiamSearch, trangThaiSearch, pageable);
    }


    public long countSearchNSX(String tenSearch, Integer hinhThucSearch, Integer loaiGiamSearch, Integer trangThaiSearch, Pageable pageable) {
        return repo.searchVoucher(tenSearch, hinhThucSearch, loaiGiamSearch, trangThaiSearch, pageable).getTotalElements();
    }

    public void save(@Valid Voucher voucher) {
        repo.save(voucher);
    }

    public Voucher getOneVoucherById(Integer id) {
        return repo.findById(id).get();
    }
}
