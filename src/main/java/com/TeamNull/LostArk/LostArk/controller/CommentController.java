package com.TeamNull.LostArk.LostArk.controller;

import com.TeamNull.LostArk.LostArk.dto.CommentDto;
import com.TeamNull.LostArk.LostArk.entity.Comment;
import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.CommentRepository;
import com.TeamNull.LostArk.LostArk.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;


    @GetMapping("/{abc}")
    public Map<String, Object> commentList(@PathVariable Integer abc, @PageableDefault(size = 5) Pageable pageable) {

        Pageable pageRequest = PageRequest.of(abc - 1, pageable.getPageSize());
        Page<Comment> comments = commentRepository.findAll( pageRequest);

        List<CommentDto.CommentResponseDto> responseDtoList = comments.getContent().stream()
                .map(comment -> {
                    CommentDto.CommentResponseDto dto = new CommentDto.CommentResponseDto();
                    dto.setUser(comment.getUser());
                    dto.setTopFactorResult(comment.getTopFactorResult());
                    dto.setCreatedAt(comment.getCreatedAt());
                    dto.setContent(comment.getContent());
                    return dto;
                })
                .toList();

        int totalPages = comments.getTotalPages();
        int currentPage = comments.getNumber() + 1;
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(startPage + 4, totalPages);

        // JSON 응답 구성
        Map<String, Object> response = new HashMap<>();
        response.put("comments", responseDtoList);
        response.put("currentPage", currentPage);
        response.put("startPage", startPage);
        response.put("endPage", endPage);
        response.put("totalPages", totalPages);

        return response;


}
        @PostMapping("")
        public void addComment(@PathVariable UUID id, @RequestBody CommentDto commentDto) {
            commentService.commentAdd(commentDto.getContent(),
                                       commentDto.getPassword(),
                                       commentDto.getUser(),
                                        commentDto.getCreatedAt(),
                                        commentDto.getTopFactorResult(),
                                        commentDto.getNickname()
            );
        }

}


