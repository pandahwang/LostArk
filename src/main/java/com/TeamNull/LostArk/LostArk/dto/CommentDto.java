package com.TeamNull.LostArk.LostArk.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class CommentDto {
    private int id;
    private Timestamp createdAt;
    private String content;
    private String password;
    private String nickname;
    private UUID userID; // User 대신 UUID로 변경
    private Timestamp updatedAt;

    // Jackson이 직렬화/역직렬화를 위해 기본 생성자가 필요합니다.
    public CommentDto() {}

    public CommentDto(int id,
                      Timestamp createdAt,
                      Timestamp updatedAt,
                      String content,
                      String password,
                      String nickname,
                      UUID userID) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.content = content;
        this.password = password;
        this.nickname = nickname;
        this.userID = userID;
    }

    @Data
    public static class CommentResponseDto  {
        private int id;
        private Timestamp createdAt;
        private String content;
        private UUID userID;
        private String topFactorResult;
        private String nickname;

        // 기본 생성자
        public CommentResponseDto() {}

        public CommentResponseDto(int id,
                                  Timestamp createdAt,
                                  String content,
                                  UUID userID,
                                  String topFactorResult,
                                  String nickname) {
            this.id = id;
            this.createdAt = createdAt;
            this.content = content;
            this.userID = userID;
            this.topFactorResult = topFactorResult;
            this.nickname = nickname;
        }
    }


}