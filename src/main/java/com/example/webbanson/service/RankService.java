package com.example.webbanson.service;

import com.example.webbanson.model.Rank;
import com.example.webbanson.repo.RankRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankService {
    private final RankRepo repo;

    public Rank getRankById(Integer id) {
        return repo.findById(id).get();
    }
}
