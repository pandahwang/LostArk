package com.TeamNull.LostArk.LostArk.controller;

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
import java.net.MalformedURLException;
import java.net.URL;


@RestController
@RequiredArgsConstructor
public class OuterDataController {


    // 목표 : Get 요청 시 api로부터 데이터를 받아와 저장 후 반환하도록 구현.
    @GetMapping("/statistic/alluser")
    public JSONObject alluser() throws IOException, ParseException {
        String result;
        URL url = new URL("https://asia-northeast3-loasearch.cloudfunctions.net/app/total?minLv=&maxLv=");
        BufferedReader bf;
        bf = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
        result = bf.readLine();

        // 받아온 데이터를 JSON으로 변환
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(result);

        return jsonObject;
    }


}
