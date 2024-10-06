package com.TeamNull.LostArk.LostArk.repository;


import com.TeamNull.LostArk.LostArk.entity.Comment;
import com.TeamNull.LostArk.LostArk.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Page<Comment> findAll(Pageable pageable); //데이터베이스를 페이지화하고, JPA를 통해 데이터베이스의 자료 추출
    Optional<Comment> findByUserIdAndId(UUID userID, int id);
    List<Comment> findByTopFactorResultContainingIgnoreCase(String topFactorResult);
}
