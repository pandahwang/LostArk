package com.TeamNull.LostArk.LostArk.service;


import com.TeamNull.LostArk.LostArk.entity.Comment;
import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.CommentRepository;
import com.TeamNull.LostArk.LostArk.repository.ResultRepository;
import com.TeamNull.LostArk.LostArk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.module.ResolutionException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ResultRepository resultRepository;
    private final UserRepository userRepository;



    public void commentAdd( String content,
                            String password,
                            String nickname,
                            UUID id
                            )
    {
        Comment comment = new Comment();

        String topFactorResult = resultRepository.findByUserId(id)
                .map(result -> result.getTopFactor1().getJobName())
                .orElseThrow(() -> new IllegalArgumentException("No result found for user ID: " + id));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No user found for ID: " + id));

        comment.setTopFactorResult(topFactorResult);
        comment.setUser(user);
        comment.setPassword(password);
        comment.setContent(content);
        comment.setNickName(nickname);

        commentRepository.save(comment);
    }


}

