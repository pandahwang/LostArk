package com.TeamNull.LostArk.LostArk.controller;

import com.TeamNull.LostArk.LostArk.dto.OuterDataDto;
import com.TeamNull.LostArk.LostArk.service.OuterDataService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OuterDataController {

    private final OuterDataService outerDataService;

    // 목표 : Get 요청 시 api로부터 데이터를 받아와 저장 후 반환하도록 구현.
    @GetMapping("/statistics/alluser")
    public List<OuterDataDto> alluser() {
        List<OuterDataDto> resData = null;
        try {
            resData = outerDataService.read();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return resData;
    }

    // 데이터 저장 테스트용 메서드
    @PutMapping("/statistics/alluser")
        public void createAlluser() throws IOException, ParseException{
        outerDataService.create();
    }


}
