package com.TeamNull.LostArk.LostArk.controller;

import com.TeamNull.LostArk.LostArk.dto.CommentDto;
import com.TeamNull.LostArk.LostArk.entity.Comment;
import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.CommentRepository;
import com.TeamNull.LostArk.LostArk.repository.UserRepository;
import com.TeamNull.LostArk.LostArk.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;


@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @GetMapping("/{abc}")   //abc라는 정수를 URL에서 추출합니다.
    public Map<String, Object> commentList(
            @PathVariable Integer abc, //추출한 정수를 저장할 변수 만듬
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) //페이지의 규칙을 만듬
            Pageable pageable //만든 규칙을 저장할 변수 생성
    ) {

        Pageable pageRequest = PageRequest.of(abc - 1, pageable.getPageSize(), pageable.getSort()); //추출된 정수와 규칙으로 페이지 변수를 만듬
        Page<Comment> comments = commentRepository.findAll(pageRequest);
        // commentRepository를 통해 추출된 데이터베이스의 자료를 만든 페이지에 적용 후 변수를 만듬

        List<CommentDto.CommentResponseDto> responseDtoList = comments.getContent().stream()//데이터베이스에서 가져온 자료 중에서 각 페이지에 포함된 댓글 목록을 추출
                //더 정확히는 댓글 content가 아닌 새로만들 댓글 목록이다.
                .map(comment -> {     // DTO를 통해 원하는 자료(댓글 목록의 자료)를 가진 리스트를 만듬
                    CommentDto.CommentResponseDto dto = new CommentDto.CommentResponseDto(
                            comment.getId(),
                            comment.getCreatedAt(),
                            comment.getContent(),
                            comment.getUser().getId(),
                            comment.getTopFactorResult(),
                            comment.getNickName()
                    );

                    return dto;
                })
                .toList();


        int totalPages = comments.getTotalPages(); //데이터베이스 페이지에 있는 페이지를 이용해 규칙성 정수를 만듬
        int currentPage = comments.getNumber() + 1;
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(startPage + 4, totalPages);

        // JSON 응답 구성
        Map<String, Object> response = new HashMap<>(); //맵을 사용해 규칙성 정수와 원하는 자료를 가진 리스트를 추력함
        response.put("comments", responseDtoList);
        response.put("currentPage", currentPage);
        response.put("startPage", startPage);
        response.put("endPage", endPage);
        response.put("totalPages", totalPages);

        return response;


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





