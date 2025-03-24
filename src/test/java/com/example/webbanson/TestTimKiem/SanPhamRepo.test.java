package com.example.webbanson.TestTimKiem;

import com.example.webbanson.model.SanPham;
import com.example.webbanson.repo.SanPhamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SanPhamRepoTest {

    @Autowired
    private SanPhamRepo sanPhamRepo;

}
