package com.TeamNull.LostArk.LostArk.repository;


import com.TeamNull.LostArk.LostArk.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Page<Comment> findByUser(Long user, Pageable pageable);
}
