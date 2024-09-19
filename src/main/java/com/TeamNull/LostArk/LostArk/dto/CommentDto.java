package com.TeamNull.LostArk.LostArk.dto;


import com.TeamNull.LostArk.LostArk.entity.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentDto {


    private int id;
    private Timestamp createdAt;
    private String content;
    private String password;
    private User name;
    private String result;

    @Data
    public static class CommentResponseDto  {

}
