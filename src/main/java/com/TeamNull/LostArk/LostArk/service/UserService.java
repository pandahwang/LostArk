package com.TeamNull.LostArk.LostArk.service;


import com.TeamNull.LostArk.LostArk.dto.UserDto;
import com.TeamNull.LostArk.LostArk.entity.User;
import com.TeamNull.LostArk.LostArk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(UserDto userDto){

        User user = new User();
        user.setQuestion1(userDto.getQuestion1());
        user.setQuestion2(userDto.getQuestion2());
        user.setQuestion3(userDto.getQuestion3());
        user.setQuestion4(userDto.getQuestion4());
        user.setQuestion5(userDto.getQuestion5());
        user.setQuestion6(userDto.getQuestion6());
        user.setQuestion7(userDto.getQuestion7());
        user.setQuestion8(userDto.getQuestion8());
        user.setQuestion9(userDto.getQuestion9());
        user.setQuestion10(userDto.getQuestion10());

        return userRepository.save(user);
    }


}
