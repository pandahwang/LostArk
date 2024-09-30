package com.TeamNull.LostArk.LostArk.service;

import com.TeamNull.LostArk.LostArk.Job.JobAttributes;
import com.TeamNull.LostArk.LostArk.Job.TopFactor;
import com.TeamNull.LostArk.LostArk.dto.OuterDataDto;
import com.TeamNull.LostArk.LostArk.entity.OuterData;
import com.TeamNull.LostArk.LostArk.repository.OuterDataRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OuterDataService {

    private final OuterDataRepository outerDataRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // json 파일을 읽어와서 DB에 저장하는 메소드
    private Map<String, JobAttributes> loadJobAttributesFromJson() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/LostArk.json");
            return objectMapper.readValue(inputStream, new TypeReference<Map<String,JobAttributes>>() {});
        } catch (Exception e) {
            throw new RuntimeException("json 파일에서 불러오기 실패", e);
        }
    }

    private final Map<String, JobAttributes> jobAttributesMap = loadJobAttributesFromJson();


    // 매일 00시에 실행
    @Scheduled(cron = "0 0 0 * * *")
    public void update() throws IOException, ParseException {
        create();
    }

    // outerData DB에 저장
    public void create() throws IOException, ParseException {
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
        outerData.setId(1);
        outerDataRepository.save(outerData);
    }

    public List<OuterDataDto> read() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Optional<OuterData> outerData = outerDataRepository.findById(1);
        List<String> jobNames = new ArrayList<>(jobAttributesMap.keySet());

        if(outerData.isPresent()){
            OuterData result = outerData.get();
            List<OuterDataDto> resData = new ArrayList<>();
            for(String jobName : jobNames){
                String methodName = "get" + jobName.substring(0, 1).toUpperCase() + jobName.substring(1);
                JobAttributes jobAttributes = jobAttributesMap.get(jobName);
                try {
                    Method method = result.getClass().getMethod(methodName);
                    int value = (int) method.invoke(result);
                    resData.add(new OuterDataDto(jobAttributes.getJobName(), value, jobAttributes.getIcon()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            resData = resData.stream()
                    .sorted(Comparator.comparingDouble(OuterDataDto::getValue).reversed())
                    .collect(Collectors.toList());
            return resData;

        }

        return null;
    }
}
