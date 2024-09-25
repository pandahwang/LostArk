package com.TeamNull.LostArk.LostArk.service;


import com.TeamNull.LostArk.LostArk.entity.Comment;
import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.CommentRepository;
import com.TeamNull.LostArk.LostArk.repository.ResultRepository;
import com.TeamNull.LostArk.LostArk.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;



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
                            UUID userId
                            )
    {
        Comment comment = new Comment();

        String topFactorResult = resultRepository.findByUserId(userId)
                        .map(result -> result.getTopFactor1().getJobName())
                                .orElseThrow(()-> new IllegalArgumentException("zzzzz" + userId));

        User user = userRepository.findById(userId)
                        .orElseThrow(()-> new IllegalArgumentException("sadw"));

        comment.setTopFactorResult(topFactorResult);
        comment.setUser(user);
        comment.setPassword(password);
        comment.setContent(content);
        comment.setNickName(nickname);

        commentRepository.save(comment);
    }


}

