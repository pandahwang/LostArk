package com.TeamNull.LostArk.LostArk.controller;

import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.ResultRepository;
import com.TeamNull.LostArk.LostArk.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ResultController {

    private final ResultRepository resultRepository;
    private final ResultService resultService;

    @GetMapping("/result/{id}")
    public Optional<User> user(@PathVariable UUID id) {
        return resultService.user(id);
    }
}
