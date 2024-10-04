package com.TeamNull.LostArk.LostArk.service;


import com.TeamNull.LostArk.LostArk.dto.CommentDto;
import com.TeamNull.LostArk.LostArk.entity.Comment;
import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.CommentRepository;
import com.TeamNull.LostArk.LostArk.repository.ResultRepository;
import com.TeamNull.LostArk.LostArk.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ResultRepository resultRepository;
    private final UserRepository userRepository;

    public Map<String, Object> getComments(int page,@PageableDefault(size = 5,sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort()));
        List<CommentDto.CommentResponseDto> responseDtoList = comments.getContent().stream()
                .map(comment -> new CommentDto.CommentResponseDto(
                        comment.getId(),
                        comment.getCreatedAt(),
                        comment.getContent(),
                        comment.getUser().getId(),
                        comment.getTopFactorResult(),
                        comment.getNickName()
                )).toList();

        int totalPage = comments.getTotalPages();
        int currentPage = comments.getNumber() + 1;
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(totalPage, startPage + 4);

        Map<String, Object> response = new HashMap<>();
        response.put("comments", responseDtoList);
        response.put("totalPages", totalPage);
        response.put("currentPage", currentPage);
        response.put("startPage", startPage);
        response.put("endPage", endPage);

        return response;
    }

    public void getAddComment( String content,
                            String password,
                            String nickname,
                            UUID userID
                            )
    {
        Comment comment = new Comment();

        comment.setNickName(nickname);
        comment.setPassword(password);
        comment.setContent(content);
        String topFactorResult = resultRepository.findByUserId(userID)
                        .map(result -> result.getTopFactor1().getJobName())
                                .orElseThrow(()-> new IllegalArgumentException("사용자의 정보가 존재하지 않습니다.." + userID));
        comment.setTopFactorResult(topFactorResult);

        User user = userRepository.findById(userID)
                        .orElseThrow(()-> new IllegalArgumentException("사용자의 아이디가 존재하지 않습니다.."));
        comment.setUser(user);


        commentRepository.save(comment);
    }

    public ResponseEntity<String> getCommentDelete(UUID userID,
                        Integer commentId,
                        String password){
        if (password == null || password.isEmpty()) {
            return  ResponseEntity.badRequest().body("비밀번호가 없습니다.");
        }

      return commentRepository.findByUserIdAndId(userID, commentId)
                .filter(comment -> comment.getPassword().equals(password))
                .map(comment -> {
                    commentRepository.deleteById(commentId);
                    return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다");
                }).orElseGet(()->
        ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("해당 사용자가 작성한 댓글을 찾을 수 없습니다."));
    }


    public ResponseEntity<String> getCommentUpdate(UUID userID, Integer commentId, String password, String content) {
        if (password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body("비밀번호를 입력해주세요.");
        }
        return commentRepository.findByUserIdAndId(userID, commentId)
                .filter(comment -> comment.getPassword().equals(password))
                .map(comment -> {
                    comment.setContent(content);  // 댓글 내용 업데이트
                    commentRepository.save(comment);  // 업데이트된 댓글 저장
                    return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("해당 사용자의 댓글을 찾을 수 없습니다."));
    }





}

