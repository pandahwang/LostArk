package com.TeamNull.LostArk.LostArk.controller;

import com.TeamNull.LostArk.LostArk.dto.UserDto;
import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.UserRepository;
import com.TeamNull.LostArk.LostArk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UUID> createUser(@RequestBody UserDto userDto) {

        // User 객체를 저장
        User savedUser = userService.saveUser(userDto);
        //User 객체를 응답으로 반환
        return ResponseEntity.ok(savedUser.getId());
    }
}