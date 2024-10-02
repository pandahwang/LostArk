package com.TeamNull.LostArk.LostArk.controller;

import com.TeamNull.LostArk.LostArk.dto.CommentDto;
import com.TeamNull.LostArk.LostArk.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;


@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @GetMapping("/{abc}")
    public Map<String, Object> commentAdd(@PathVariable Integer abc,
                                          @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.ASC)
                                          Pageable pageable) {
        return commentService.getComments(abc, pageable);
    }

    @PostMapping("/{userId}")
    public void addComment(@PathVariable UUID userId, @RequestBody CommentDto commentDto)
    {
        commentService.creation(commentDto.getContent(),
                commentDto.getPassword(),
                commentDto.getNickname(),
                userId
        );
    }

    @DeleteMapping("/delete/{userId}/{commentId}")
    public ResponseEntity<String> commentDelete(@PathVariable UUID userId,
                                                @PathVariable Integer commentId,
                                                @RequestBody CommentDto dropComment)
    {
       return commentService.removal(userId,commentId,dropComment.getPassword());
    }

    @PutMapping("/update/{userId}/{commentId}")
    public ResponseEntity<String> commentUpdate(@PathVariable UUID userId,
                                        @PathVariable int commentId,
                                        @RequestBody CommentDto updatedComment)
    {
       return commentService.edition(userId,commentId,updatedComment.getPassword(),updatedComment.getContent());
    }
}





