package com.TeamNull.LostArk.LostArk.dto;

import com.TeamNull.LostArk.LostArk.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto {

    private int id;
    private User user;
    private String topFactor1;
    private String topFactor2;
    private String topFactor3;
    private String topFactor4;
    private String topFactor5;
}
