package com.example.webbanson.repo;

import com.example.webbanson.model.MauSac;
import com.example.webbanson.model.Nsx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NSXRepo extends JpaRepository<Nsx, Integer> {
}
