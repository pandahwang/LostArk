package com.TeamNull.LostArk.LostArk.controller;

import com.TeamNull.LostArk.LostArk.dto.OuterDataDto;
import com.TeamNull.LostArk.LostArk.service.OuterDataService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class OuterDataController {

    private final OuterDataService outerDataService;


    // 목표 : Get 요청 시 api로부터 데이터를 받아와 저장 후 반환하도록 구현.

    @GetMapping("/statistic/alluser")
    public OuterDataDto alluser() throws IOException, ParseException {
        outerDataService.create();
        OuterDataDto outerDataDto = outerDataService.read();
        return outerDataDto;
    }


}
