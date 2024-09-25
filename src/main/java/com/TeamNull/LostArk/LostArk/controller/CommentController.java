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

    @GetMapping("/{abc}")
    public Map<String, Object> commentList(
            @PathVariable Integer abc,
            @PageableDefault(size = 5,sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    )
    {

        Pageable pageRequest = PageRequest.of(abc - 1, pageable.getPageSize());
        Page<Comment> comments = commentRepository.findAll( pageRequest);

        List<CommentDto.CommentResponseDto> responseDtoList = comments.getContent().stream()
                .map(comment -> {
                    CommentDto.CommentResponseDto dto = new CommentDto.CommentResponseDto(
                            comment.getCreatedAt(),
                            comment.getContent(),
                            comment.getUser().getId(),
                            comment.getTopFactorResult(),
                            comment.getNickName()
                    );

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
        @PostMapping("/{userId}")
        public void addComment(@PathVariable UUID userId , @RequestBody CommentDto commentDto) {

            commentService.commentAdd(commentDto.getContent(),
                                       commentDto.getPassword(),
                                        commentDto.getNickname(),
                                         userId
            );
        }

    @DeleteMapping("/delete/{userId}/{commentId}")
    public ResponseEntity<String> commentDelete(@PathVariable UUID userId,
                                                @PathVariable int commentId,
                                                @RequestBody Map<String, String> requestBody) {
        // 요청 본문에서 password 가져오기
        String password = requestBody.get("password");

        if (password == null || password.isEmpty()) {
            return new ResponseEntity<>("비밀번호가 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // userId에 해당하는 사용자가 있는지 확인
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            // commentId에 해당하는 댓글을 찾음
            Optional<Comment> comment = commentRepository.findById(commentId);

            if (comment.isPresent() && comment.get().getUser().getId().equals(userId)) {
                // 댓글의 비밀번호가 입력된 비밀번호와 일치하는지 확인
                if (!comment.get().getPassword().equals(password)) {
                    return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
                }

                // 비밀번호가 일치하면 댓글 삭제
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

    @PutMapping("/update/{userId}/{commentId}")
    public ResponseEntity<String> commentUpdate(@PathVariable UUID userId,
                                                @PathVariable int commentId,
                                                @RequestBody CommentDto updatedComment) {
        if (updatedComment == null || updatedComment.getPassword() == null) {
            return new ResponseEntity<>("요청 본문 또는 비밀번호가 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // userId에 해당하는 사용자가 있는지 확인
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            // commentId에 해당하는 댓글을 찾음
            Optional<Comment> comment = commentRepository.findById(commentId);

            if (comment.isPresent() && comment.get().getUser().getId().equals(userId)) {
                // 댓글의 비밀번호가 입력된 비밀번호와 일치하는지 확인
                if (!comment.get().getPassword().equals(updatedComment.getPassword())) {
                    return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
                }

                // 비밀번호가 일치하면 댓글 업데이트
                Comment existingComment = comment.get();
                existingComment.setContent(updatedComment.getContent()); // 댓글 내용 업데이트

                commentRepository.save(existingComment); // 변경된 댓글 저장

                return new ResponseEntity<>("댓글이 성공적으로 업데이트되었습니다.", HttpStatus.OK);
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





