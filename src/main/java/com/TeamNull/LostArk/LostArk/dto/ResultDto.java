package com.TeamNull.LostArk.LostArk.dto;

import com.TeamNull.LostArk.LostArk.Job.TopFactor;
import com.TeamNull.LostArk.LostArk.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto {
    private String name;
    private double value;
    private String icon;
    private String color;

    public ResultDto(String name, double value, String icon, String color) {
        this.name = name;
        this.value = value;
        this.icon = icon;
        this.color = color;
    }
}
