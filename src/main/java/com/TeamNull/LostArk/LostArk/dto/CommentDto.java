package com.TeamNull.LostArk.LostArk.dto;


import com.TeamNull.LostArk.LostArk.entity.User;
import lombok.Data;

@Data
public class CommentDto {


    private int id;
    private String createdAt;
    private String content;
    private String password;
    private User user;

}
