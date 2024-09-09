package com.TeamNull.LostArk.LostArk.controller;

import com.TeamNull.LostArk.LostArk.dto.OuterDataDto;
import com.TeamNull.LostArk.LostArk.entity.OuterData;
import com.TeamNull.LostArk.LostArk.repository.OuterDataRepository;
import com.TeamNull.LostArk.LostArk.service.OuterDataService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;


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
