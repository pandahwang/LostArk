package com.TeamNull.LostArk.LostArk.repository;


import com.TeamNull.LostArk.LostArk.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Page<Comment> findAll(Pageable pageable); //데이터베이스를 페이지화하고, JPA를 통해 데이터베이스의 자료 추출
}
