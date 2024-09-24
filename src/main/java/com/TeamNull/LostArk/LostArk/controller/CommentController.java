package com.TeamNull.LostArk.LostArk.controller;

import com.TeamNull.LostArk.LostArk.dto.CommentDto;
import com.TeamNull.LostArk.LostArk.entity.Comment;
import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.CommentRepository;
import com.TeamNull.LostArk.LostArk.repository.ResultRepository;
import com.TeamNull.LostArk.LostArk.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @GetMapping("/{abc}")
    public Map<String, Object> commentList(@PathVariable Integer abc, @PageableDefault(size = 5) Pageable pageable) {

        Pageable pageRequest = PageRequest.of(abc - 1, pageable.getPageSize());
        Page<Comment> comments = commentRepository.findAll( pageRequest);

        List<CommentDto.CommentResponseDto> responseDtoList = comments.getContent().stream()
                .map(comment -> {
                    CommentDto.CommentResponseDto dto = new CommentDto.CommentResponseDto();
                    dto.setUserID(comment.getUser().getId());
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
        @PostMapping("/{id}")
        public void addComment(@PathVariable UUID id , @RequestBody CommentDto commentDto) {

            commentService.commentAdd(commentDto.getContent(),
                                       commentDto.getPassword(),
                                       commentDto.getCreatedAt(),
                                        commentDto.getNickname(),
                                        id
            );
        }

    @DeleteMapping("/delete/{userId}/{commentId}")
    public ResponseEntity<String> commentDelete(@PathVariable UUID userId, @PathVariable int commentId) {
        // userId에 해당하는 사용자가 있는지 확인
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            // commentId에 해당하는 댓글을 찾음
            Optional<Comment> comment = commentRepository.findById(commentId);

            if (comment.isPresent() && comment.get().getUser().getId().equals(userId)) {
                // 해당 사용자가 작성한 댓글이 맞으면 삭제
                commentRepository.deleteById(commentId);
                return new ResponseEntity<>("댓글이 성공적으로 삭제되었습니다.", HttpStatus.OK);
            } else {
                // 댓글이 없거나 사용자의 댓글이 아닌 경우
                return new ResponseEntity<>("해당 사용자가 작성한 댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
            }
        } else {
            // 해당 사용자가 없을 때
            return new ResponseEntity<>("해당 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
    }


}


