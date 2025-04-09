package com.example.webbanson.repo;

import com.example.webbanson.model.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepo extends JpaRepository<Rank, Integer> {
}
