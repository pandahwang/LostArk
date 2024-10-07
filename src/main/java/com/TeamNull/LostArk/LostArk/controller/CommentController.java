package com.TeamNull.LostArk.LostArk.controller;

import com.TeamNull.LostArk.LostArk.dto.CommentDto;
import com.TeamNull.LostArk.LostArk.entity.Comment;
import com.TeamNull.LostArk.LostArk.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;


@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @GetMapping("/{abc}")
    public Map<String, Object> commentPage(@PathVariable Integer abc,
                                          @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
                                          Pageable pageable) {
        return commentService.getComments(abc, pageable);
    }

    @PostMapping("/{userID}")
    public void addComment(@PathVariable("userID") UUID userID, @RequestBody CommentDto commentDto)
    {
        commentService.getAddComment(commentDto.getContent(),
                commentDto.getPassword(),
                commentDto.getNickname(),
                userID
        );
    }

    @DeleteMapping("/delete/{userID}/{commentId}")
    public ResponseEntity<String> commentDelete(@PathVariable("userID") UUID userID,
                                                @PathVariable Integer commentId,
                                                @RequestBody CommentDto dropComment)
    {
       return commentService.getCommentDelete(userID,commentId,dropComment.getPassword());
    }

    @PutMapping("/update/{userID}/{commentId}")
    public ResponseEntity<String> commentUpdate(@PathVariable("userID") UUID userID,
                                                @PathVariable int commentId,
                                                @RequestBody CommentDto updatedComment) {
        return commentService.getCommentUpdate(userID, commentId, updatedComment.getPassword(), updatedComment.getContent());
    }


    @PostMapping("/search")
    public List<CommentDto.CommentResponseDto> commentSearch(@RequestBody CommentDto.CommentResponseDto commentDto){

      return commentService.getCommentSearch(commentDto.getTopFactorResult());

    }

}





