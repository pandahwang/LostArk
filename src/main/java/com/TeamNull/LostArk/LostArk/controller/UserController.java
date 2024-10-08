package com.TeamNull.LostArk.LostArk.controller;

import com.TeamNull.LostArk.LostArk.dto.UserDto;
import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.UserRepository;
import com.TeamNull.LostArk.LostArk.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UUID> createUser(@RequestBody UserDto userDto) {
        User newUser = userService.saveUser(userDto);
        return ResponseEntity.ok(newUser.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        // id로 User 객체를 찾아 반환
        return ResponseEntity.ok(userRepository.findById(id).get());
    }
}