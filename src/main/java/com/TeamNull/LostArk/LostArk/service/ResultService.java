package com.TeamNull.LostArk.LostArk.service;

import com.TeamNull.LostArk.LostArk.Job.JobAttributes;
import com.TeamNull.LostArk.LostArk.Job.TopFactor;
import com.TeamNull.LostArk.LostArk.dto.ResultDto;
import com.TeamNull.LostArk.LostArk.entity.Data;
import com.TeamNull.LostArk.LostArk.entity.Result;
import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.DataRepository;
import com.TeamNull.LostArk.LostArk.repository.ResultRepository;
import com.TeamNull.LostArk.LostArk.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResultService {

    private final ResultRepository resultRepository;
    private final UserRepository userRepository;
    private final DataRepository dataRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public ResultService(ResultRepository resultRepository, UserRepository userRepository, DataRepository dataRepository) {
        this.resultRepository = resultRepository;
        this.userRepository = userRepository;
        this.dataRepository = dataRepository;
    }

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

   @Transactional
    public void top5 (UUID id) {
       User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("user not found"));
//테스트 항목 평균값 계산
       double avg1 = (user.getQuestion1() + user.getQuestion2()) / 2.0;
       double avg2 = (user.getQuestion3() + user.getQuestion4()) / 2.0;
       double avg3 = (user.getQuestion5() + user.getQuestion6()) / 2.0;
       double avg4 = (user.getQuestion7() + user.getQuestion8()) / 2.0;
       double avg5 = (user.getQuestion9() + user.getQuestion10()) / 2.0;

//각 성향별 점수 책정
       Map<JobAttributes, Double> jobScores = jobAttributesMap.values().stream()
               .collect(Collectors.toMap(
                       job -> job,
                       job -> avg1 * job.getAgreeableness() +
                               avg2 * job.getConscientiousness() +
                               avg3 * job.getExtraversion() +
                               avg4 * job.getOpenness() +
                               avg5 * job.getNeuroticism()
               ));

       // 상위 5개 직업 선별

       List<TopFactor> top5Jobs = jobScores.entrySet().stream()
               .sorted(Map.Entry.<JobAttributes, Double>comparingByValue().reversed())
               .limit(5)
               .map(entry -> new TopFactor(entry.getKey().getJobName(), entry.getValue()))
               .collect(Collectors.toList());




       Result result = new Result();
       result.setUser(user);
//1위 부터 5위까지 저장
       result.setTopFactor1(top5Jobs.size() > 0 ? new TopFactor(top5Jobs.get(0).getJobName(), top5Jobs.get(0).getValue()) : null);
       result.setTopFactor2(top5Jobs.size() > 1 ? new TopFactor(top5Jobs.get(1).getJobName(), top5Jobs.get(1).getValue()) : null);
       result.setTopFactor3(top5Jobs.size() > 2 ? new TopFactor(top5Jobs.get(2).getJobName(), top5Jobs.get(2).getValue()) : null);
       result.setTopFactor4(top5Jobs.size() > 3 ? new TopFactor(top5Jobs.get(3).getJobName(), top5Jobs.get(3).getValue()) : null);
       result.setTopFactor5(top5Jobs.size() > 4 ? new TopFactor(top5Jobs.get(4).getJobName(), top5Jobs.get(4).getValue()) : null);

       resultRepository.save(result);

//Data 테이블 매핑
       Data data = dataRepository.findById(1).orElseThrow(()-> new IllegalArgumentException("Data not found"));
//저장될때마다 createdAt 갱신
       data.setCreatedAt(Timestamp.from(Instant.now()));
//1위 직업 카운트 1 증가
       String className = result.getTopFactor1().getJobName();
       String key = jobAttributesMap.entrySet().stream()
               .filter(entry -> entry.getValue().getJobName().equals(className))
               .map(Map.Entry::getKey)
               .findFirst()
               .orElse(null);

       if (key != null) {
           try {
               String getMethodName = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
               Method getMethod = data.getClass().getMethod(getMethodName);
               int currentValue = (int) getMethod.invoke(data);

               String setMethodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
               Method setMethod = data.getClass().getMethod(setMethodName, int.class);
               setMethod.invoke(data, currentValue + 1);

           } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
               e.printStackTrace();
           }
       }

       dataRepository.save(data);

   }

   // user UUID로 결과 반환
    public List<ResultDto> getResult(UUID id) {
        Result result = resultRepository.findByUserId(id).orElseThrow(() -> new IllegalArgumentException("result not found"));
        List<ResultDto> resultDtoList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            try {
                Method getTopFactorMethod = result.getClass().getMethod("getTopFactor" + i);
                TopFactor topFactor = (TopFactor) getTopFactorMethod.invoke(result);
                String jobName = topFactor.getJobName();
                if (topFactor != null) {
                    String key = jobAttributesMap.entrySet().stream()
                            .filter(entry -> entry.getValue().getJobName().equals(jobName))
                            .map(Map.Entry::getKey)
                            .findFirst()
                            .orElse(null);
                    JobAttributes jobAttribute = jobAttributesMap.get(key);
                    if (jobAttribute != null) {
                        resultDtoList.add(new ResultDto(topFactor.getJobName(), topFactor.getValue(),
                                jobAttribute.getIcon(), jobAttribute.getColor()));
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return resultDtoList;
    }
}