package com.TeamNull.LostArk.LostArk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class DataDto {

    private String name;
    private int value;
    private String icon;

    public DataDto(String name, int value, String icon) {
        this.name = name;
        this.value = value;
        this.icon = icon;
    }
}
