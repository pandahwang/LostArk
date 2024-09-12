package com.TeamNull.LostArk.LostArk.controller;

import com.TeamNull.LostArk.LostArk.Job.JobAttributes;
import com.TeamNull.LostArk.LostArk.dto.UserDto;
import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.ResultRepository;
import com.TeamNull.LostArk.LostArk.repository.UserRepository;
import com.TeamNull.LostArk.LostArk.service.ResultService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ResultController {

    private final ResultRepository resultRepository;
    private final ResultService resultService;
    private final UserRepository userRepository;

    @PostMapping("/result/{id}")
    public void top5 (@PathVariable UUID id){
        resultService.top5(id);
    }
}