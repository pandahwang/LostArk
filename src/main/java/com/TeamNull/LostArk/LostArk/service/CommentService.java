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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ResultRepository resultRepository;
    private final UserRepository userRepository;

    public Map<String, Object> getComments(int page, Pageable pageable) {
        Pageable pageRequest = PageRequest.of(page - 1, pageable.getPageSize(), pageable.getSort());
        Page<Comment> comments = commentRepository.findAll(pageRequest);

        List<CommentDto.CommentResponseDto> responseDtoList = comments.getContent().stream()
                .map(comment -> new CommentDto.CommentResponseDto(
                        comment.getId(),
                        comment.getCreatedAt(),
                        comment.getPassword(),
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
        response.put("TOTALPAGE", totalPage);
        response.put("CURRENTPAGE", currentPage);
        response.put("STARTPAGE", startPage);
        response.put("ENDPAGE", endPage);

        return response;
    }

    public void creation( String content,
                            String password,
                            String nickname,
                            UUID userId
                            )  //Controller에서 전이된 변수 인자를 받음
    {
        Comment comment = new Comment();//엔티티 변수를 만들어 set을 통해 body로 부터 추출한 자료를 데이터베이스의 데이터를 추가

        comment.setPassword(password);
        comment.setContent(content);
        comment.setNickName(nickname);
        String topFactorResult = resultRepository.findByUserId(userId)//resultRepository를 통해 userId를 찾아 userId가 포함된 모든 인스턴스(행)을 자져옴.
                        .map(result -> result.getTopFactor1().getJobName())//가져온 인스턴스에서 getJobName 속성의 String 자료만 추출함
                                .orElseThrow(()-> new IllegalArgumentException("없습니다" + userId));
        comment.setTopFactorResult(topFactorResult);

        User user = userRepository.findById(userId)// url로 부터 추출한 userId를 userRepository를 통해 찾아 Comment의 User user 변수에 저장
                        .orElseThrow(()-> new IllegalArgumentException("없습니다."));
        comment.setUser(user); //그리고 set을 통해 데이터베이스의 데이터를 추가


        commentRepository.save(comment);//설정이 완료된 Comment 객체를 데이터베이스에 저장합니다.
    }

    public ResponseEntity<String> removal(UUID userId,
                        Integer commentId,
                        String password){
        if (password == null || password.isEmpty()) {
            return  ResponseEntity.badRequest().body("비밀번호가 없습니다.");
        }

      return commentRepository.findByUserIdAndId(userId, commentId)
                .filter(comment -> comment.getPassword().equals(password))
                .map(comment -> {
                    commentRepository.deleteById(commentId);
                    return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다");
                }).orElseGet(()->
        ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("해당 사용자가 작성한 댓글을 찾을 수 없습니다."));
    }


    public ResponseEntity<String> edition(UUID userId, Integer commentId, String password, String Content)
    {
        if (password == null || password.isEmpty()) {
            return   ResponseEntity.badRequest().body("요청 본문 또는 비밀번호가 없습니다.");
        }
        return commentRepository.findByUserIdAndId(userId, commentId)
                .filter(comment -> comment.getPassword().equals(password))
                .map(comment -> {
                    comment.setContent(Content);
                    commentRepository.save(comment);
                    return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");

                }).orElseGet(()->  ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("해당 사용자가 작성한 댓글을 찾을 수 없습니다."));
    }
}

