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
    private User user;
    private String topFactorResult;
    private String nickname;

    @Data
    public static class CommentResponseDto  {

        private Timestamp createdAt;
        private String content;
        private User user;
        private String topFactorResult;

    }
}
