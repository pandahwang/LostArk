package com.TeamNull.LostArk.LostArk.controller;

import com.TeamNull.LostArk.LostArk.dto.ResultDto;
import com.TeamNull.LostArk.LostArk.repository.ResultRepository;
import com.TeamNull.LostArk.LostArk.repository.UserRepository;
import com.TeamNull.LostArk.LostArk.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ResultController {

    private final ResultRepository resultRepository;
    private final ResultService resultService;
    private final UserRepository userRepository;

    @PostMapping("/results/{id}")
    public void top5 (@PathVariable UUID id){
        resultService.top5(id);
    }


    // user UUID로 찾기
    @GetMapping("/results/{id}")
    public ResponseEntity<List<ResultDto>> result(@PathVariable("id") UUID id) {
        List<ResultDto> result = resultService.getResult(id);
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}