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
    private int value;
    private String icon;

    public OuterDataDto(String name, int value, String icon) {
        this.name = name;
        this.value = value;
        this.icon = icon;
    }
}
