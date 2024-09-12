package com.TeamNull.LostArk.LostArk.service;

public class CommentService {

    private final CommentRepository commentRepository;



    public void commentAdd( String content,
                            String password,
                            User user)
    {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPassword(password);
        comment.setUser(user);
        commentRepository.save(comment);
    }

}

