package com.TeamNull.LostArk.LostArk.service;


import com.TeamNull.LostArk.LostArk.dto.CommentDto;
import com.TeamNull.LostArk.LostArk.entity.Comment;
import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.CommentRepository;
import com.TeamNull.LostArk.LostArk.repository.ResultRepository;
import com.TeamNull.LostArk.LostArk.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ResultRepository resultRepository;
    private final UserRepository userRepository;

    public Map<String, Object> getComments(int page, String searchText, Pageable pageable) {
        Page<CommentDto.CommentResponseDto> comments;

        if (searchText != null && !searchText.isEmpty()) {
            comments = getCommentSearch(searchText, PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort()));
        } else {
            comments = commentRepository.findAll(PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort()))
                    .map(comment -> new CommentDto.CommentResponseDto(
                            comment.getId(),
                            comment.getCreatedAt(),
                            comment.getContent(),
                            comment.getUser().getId(),
                            comment.getTopFactorResult(),
                            comment.getNickName()
                    ));
        }

        int totalPage = comments.getTotalPages();
        int currentPage = comments.getNumber() + 1;
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(totalPage, startPage + 4);

        Map<String, Object> response = new HashMap<>();
        response.put("comments", comments.getContent());
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

    public Page<CommentDto.CommentResponseDto> getCommentSearch(String searchText, Pageable pageable) {
        List<Comment> search = commentRepository.findByTopFactorResultContainingIgnoreCase(searchText);

        // 검색 결과를 페이지네이션 형태로 변환
        List<CommentDto.CommentResponseDto> responseDtoList = search.stream()
                .map(comment -> new CommentDto.CommentResponseDto(
                        comment.getId(),
                        comment.getCreatedAt(),
                        comment.getContent(),
                        comment.getUser().getId(),
                        comment.getTopFactorResult(),
                        comment.getNickName()
                )).toList();

        // 페이지네이션 처리
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), responseDtoList.size());

        return new PageImpl<>(responseDtoList.subList(start, end), pageable, responseDtoList.size());

    }
}

