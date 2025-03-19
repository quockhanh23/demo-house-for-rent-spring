package com.example.testspringweb.services.impl;

import com.example.testspringweb.dto.LoginRequest;
import com.example.testspringweb.dto.UserDTORequest;
import com.example.testspringweb.dto.UserDTOResponse;
import com.example.testspringweb.exption.InvalidException;
import com.example.testspringweb.models.User;
import com.example.testspringweb.repository.UserRepository;
import com.example.testspringweb.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTOResponse register(User user) {
        log.info("bắt đầu vào hàm register");
        User user1 = userRepository.save(user);
        UserDTOResponse userDTOResponse = new UserDTOResponse();
        BeanUtils.copyProperties(user1, userDTOResponse);
        log.info("userDTOResponse: {}", userDTOResponse);
        return userDTOResponse;
    }

    @Override
    public UserDTOResponse checkLogin(LoginRequest loginRequest) {
        User user = userRepository.findUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if (Objects.isNull(user)) {
            throw new InvalidException("Sai tên đăng nhập hoặc mật khẩu");
        }
        UserDTOResponse userDTOResponse = new UserDTOResponse();
        BeanUtils.copyProperties(user, userDTOResponse);
        return userDTOResponse;
    }

    @Override
    public UserDTOResponse updateUser(UserDTORequest userDTORequest) {
        log.info("bắt đầu vào hàm updateUser");
        Optional<User> optionalUser = userRepository.findById(userDTORequest.getId());
        if (optionalUser.isEmpty()) {
            log.error("optionalUser: {}", optionalUser);
            throw new InvalidException("Không tìm thấy người dùng");
        }
        if (userDTORequest.getPassword().equals(userDTORequest.getConfirmPassword())) {
            if (!optionalUser.get().getPassword().equals(userDTORequest.getPassword())) {
                optionalUser.get().setPassword(userDTORequest.getPassword());
            }
            if (!optionalUser.get().getConfirmPassword().equals(userDTORequest.getConfirmPassword())) {
                optionalUser.get().setConfirmPassword(userDTORequest.getConfirmPassword());
            }
        }
        if (!optionalUser.get().getEmail().equals(userDTORequest.getEmail())) {
            optionalUser.get().setEmail(userDTORequest.getEmail());
        }
        if (!optionalUser.get().getPhone().equals(userDTORequest.getPhone())) {
            optionalUser.get().setPhone(userDTORequest.getPhone());
        }
        User user = userRepository.save(optionalUser.get());
        UserDTOResponse userDTOResponse = new UserDTOResponse();
        BeanUtils.copyProperties(user, userDTOResponse);
        return userDTOResponse;
    }

    @Override
    public UserDTOResponse getDetailUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new InvalidException("Không tìm thấy người dùng");
        }
        UserDTOResponse userDTOResponse = new UserDTOResponse();
        BeanUtils.copyProperties(user, userDTOResponse);
        return userDTOResponse;
    }
}
