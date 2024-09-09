package com.TeamNull.LostArk.LostArk.controller;

import com.TeamNull.LostArk.LostArk.dto.OuterDataDto;
import com.TeamNull.LostArk.LostArk.entity.OuterData;
import com.TeamNull.LostArk.LostArk.repository.OuterDataRepository;
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

    private final OuterDataRepository outerDataRepository;


    // 목표 : Get 요청 시 api로부터 데이터를 받아와 저장 후 반환하도록 구현.

    @GetMapping("/statistic/alluser")
    public OuterData alluser() throws IOException, ParseException {
        String result;
        URL url = new URL("https://asia-northeast3-loasearch.cloudfunctions.net/app/total?minLv=&maxLv=");
        BufferedReader bf;
        bf = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
        result = bf.readLine();

        // 받아온 데이터를 JSON으로 변환
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
        JSONObject data = (JSONObject) jsonObject.get("data");
        JSONArray classTotalArr = (JSONArray)data.get("classTotal");

        for (int i = 0; i < classTotalArr.size(); i++) {
            JSONObject classData = (JSONObject) classTotalArr.get(i);
            String className = (String) classData.get("className");

            // 특정 클래스 이름이 있는 경우 해당 객체를 삭제
            if (className.equals("전사(남)") || className.equals("전사(여)") ||
                    className.equals("무도가(남)") || className.equals("무도가(여)") ||
                    className.equals("헌터(남)") || className.equals("헌터(여)") ||
                    className.equals("마법사") || className.equals("암살자") ||
                    className.equals("스페셜리스트")) {

                classTotalArr.remove(i);
                i--;  // remove 했으므로 인덱스 조정
            }
        }
        
        // 받아온 JSON 배열을 Dto에 넣어서 save
        OuterData outerData = new OuterData();
        for (Object obj : classTotalArr){
            JSONObject classData = (JSONObject) obj;
            String className = (String) classData.get("className");
            int classTotal = Integer.parseInt(classData.get("classTotal").toString());

            switch (className) {
                case "디스트로이어" : outerData.setDestroyer(classTotal);
                    break;
                case "버서커" : outerData.setBerserker(classTotal);
                    break;
                case "슬레이어" : outerData.setSlayer(classTotal);
                    break;
                case "워로드" : outerData.setGunlancer(classTotal);
                    break;
                case "홀리나이트" : outerData.setPaladin(classTotal);
                    break;
                case "기공사" : outerData.setSoulfist(classTotal);
                    break;
                case "배틀마스터" : outerData.setWardancer(classTotal);
                    break;
                case "브레이커" : outerData.setBreaker(classTotal);
                    break;
                case "스트라이커" : outerData.setStriker(classTotal);
                    break;
                case "인파이터" : outerData.setScrapper(classTotal);
                    break;
                case "창술사" : outerData.setGlaivier(classTotal);
                    break;
                case "건슬링어" : outerData.setGunslinger(classTotal);
                    break;
                case "데빌헌터" : outerData.setDeadeye(classTotal);
                    break;
                case "블래스터" : outerData.setArtillerist(classTotal);
                    break;
                case "스카우터" : outerData.setMachinist(classTotal);
                    break;
                case "호크아이" : outerData.setSharpshooter(classTotal);
                    break;
                case "바드" : outerData.setBard(classTotal);
                    break;
                case "서머너" : outerData.setSummoner(classTotal);
                    break;
                case "소서리스" : outerData.setSorceress(classTotal);
                    break;
                case "아르카나" : outerData.setArcanist(classTotal);
                    break;
                case "데모닉" : outerData.setShadowhunter(classTotal);
                    break;
                case "리퍼" : outerData.setReaper(classTotal);
                    break;
                case "블레이드" : outerData.setDeathblade(classTotal);
                    break;
                case "소울이터" : outerData.setSouleater(classTotal);
                    break;
                case "기상술사" : outerData.setAeromancer(classTotal);
                    break;
                case "도화가" : outerData.setArtist(classTotal);
                    break;
            }
        }
            outerData.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            outerDataRepository.save(outerData);

        return outerData;
    }


}
