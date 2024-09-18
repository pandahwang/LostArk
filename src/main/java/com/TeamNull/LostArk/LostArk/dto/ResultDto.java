package com.TeamNull.LostArk.LostArk.dto;

import com.TeamNull.LostArk.LostArk.Job.TopFactor;
import com.TeamNull.LostArk.LostArk.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDto {

    private int id;
    private User user;
    private TopFactor topFactor1;
    private TopFactor topFactor2;
    private TopFactor topFactor3;
    private TopFactor topFactor4;
    private TopFactor topFactor5;
}
