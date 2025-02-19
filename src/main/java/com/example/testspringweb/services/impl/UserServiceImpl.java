package com.example.testspringweb.services.impl;

import com.example.testspringweb.dto.UserDTO;
import com.example.testspringweb.models.User;
import com.example.testspringweb.repository.UserRepository;
import com.example.testspringweb.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO register(User user) {
        User user1 = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user1, userDTO);
        return userDTO;
    }
}
