package com.TeamNull.LostArk.LostArk.service;


import com.TeamNull.LostArk.LostArk.entity.Comment;
import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.CommentRepository;
import com.TeamNull.LostArk.LostArk.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.module.ResolutionException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;



    public void commentAdd( String content,
                            String password,
                            User user,
                            Timestamp createdAt,
                            String topFactorResult,
                            String nickname
                            )
    {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setCreatedAt(createdAt);
        comment.setPassword(password);
        comment.setContent(content);
        comment.setTopFactorResult(topFactorResult);
        comment.setNickName(nickname);
        commentRepository.save(comment);
    }

}

