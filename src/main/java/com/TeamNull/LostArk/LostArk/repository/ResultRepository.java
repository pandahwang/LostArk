package com.TeamNull.LostArk.LostArk.repository;

import com.TeamNull.LostArk.LostArk.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    public Optional<Result> findByUserId(UUID id);
}
