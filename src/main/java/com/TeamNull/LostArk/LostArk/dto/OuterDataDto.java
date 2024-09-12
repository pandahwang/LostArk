package com.TeamNull.LostArk.LostArk.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.sql.Timestamp;

@Getter
@Setter
public class OuterDataDto {
    private String name;
    private double percentage;
    private String icon;

    public OuterDataDto(String name, int value , Long total, String icon) {
        double percentage =  (double) value / total * 100;
        this.name = name;
        this.percentage = (double) (Math.round(percentage * 100) / 100.0);
        this.icon = icon;
    }
}
