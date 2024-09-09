package com.TeamNull.LostArk.LostArk.repository;

import com.TeamNull.LostArk.LostArk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
