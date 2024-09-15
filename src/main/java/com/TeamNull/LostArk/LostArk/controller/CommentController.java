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


@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;


    @GetMapping("/{user}/list/{abc}")
    public Map<String, Object> commentList(@PathVariable Long user, @PathVariable Integer abc, @PageableDefault(size = 5) Pageable pageable) {

        Pageable pageRequest = PageRequest.of(abc - 1, pageable.getPageSize());


        Page<Comment> comments = commentRepository.findByUser(user, pageRequest);

        // 페이지네이션 관련 정보 계산
        int totalPages = comments.getTotalPages();
        int currentPage = comments.getNumber() + 1;  // 0부터 시작하므로 +1
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(startPage + 4, totalPages);

        // JSON 응답 구성
        Map<String, Object> response = new HashMap<>();
        response.put("comments", comments.getContent());  // 댓글 리스트
        response.put("currentPage", currentPage);         // 현재 페이지 번호
        response.put("startPage", startPage);             // 시작 페이지 번호
        response.put("endPage", endPage);                 // 끝 페이지 번호
        response.put("totalPages", totalPages);           // 전체 페이지 수

        return response;  // JSON으로 응답


}
        @PostMapping("")
        public void addComment(@RequestBody CommentDto commentDto) {
            commentService.commentAdd(commentDto.getContent(), commentDto.getPassword(), commentDto.getUser());
        }

}


