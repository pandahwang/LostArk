package com.TeamNull.LostArk.LostArk.Job;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TopFactorConverter implements AttributeConverter<TopFactor, String> {

    @Override
    public String convertToDatabaseColumn(TopFactor topFactor) {
        // 예: "jobName:value" 형식으로 직렬화
        return topFactor.getJobName() + ":" + topFactor.getValue();
    }

    @Override
    public TopFactor convertToEntityAttribute(String dbData) {
        // 역직렬화: "jobName:value" 형식을 객체로 변환
        String[] data = dbData.split(":");
        return new TopFactor(data[0], Double.parseDouble(data[1]));
    }
}
