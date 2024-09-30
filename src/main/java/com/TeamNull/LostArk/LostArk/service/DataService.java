package com.TeamNull.LostArk.LostArk.service;

import com.TeamNull.LostArk.LostArk.Job.JobAttributes;
import com.TeamNull.LostArk.LostArk.dto.DataDto;
import com.TeamNull.LostArk.LostArk.entity.Data;
import com.TeamNull.LostArk.LostArk.repository.DataRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataService {

    private final DataRepository dataRepository;
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


    public List<DataDto> read() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Optional<Data> data = dataRepository.findById(1);
        List<String> jobNames = new ArrayList<>(jobAttributesMap.keySet());

        if(data.isPresent()){
            Data result = data.get();
            List<DataDto> resData = new ArrayList<>();
            for(String jobName : jobNames){
                String methodName = "get" + jobName.substring(0, 1).toUpperCase() + jobName.substring(1);
                JobAttributes jobAttributes = jobAttributesMap.get(jobName);
                try {
                    Method method = result.getClass().getMethod(methodName);
                    int value = (int) method.invoke(result);
                    resData.add(new DataDto(jobAttributes.getJobName(), value, jobAttributes.getIcon()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            resData = resData.stream()
                    .sorted(Comparator.comparingDouble(DataDto::getValue).reversed())
                    .collect(Collectors.toList());
            return resData;

        }

        return null;
    }
}