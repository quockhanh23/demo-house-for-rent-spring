package com.example.testspringweb.services;

import com.example.testspringweb.dto.LoginRequest;
import com.example.testspringweb.dto.UserDTORequest;
import com.example.testspringweb.dto.UserDTOResponse;
import com.example.testspringweb.models.User;

public interface UserService {

    UserDTOResponse register(User user);

    UserDTOResponse checkLogin(LoginRequest loginRequest);

    UserDTOResponse updateUser(UserDTORequest userDTORequest);

    UserDTOResponse getDetailUser(Long userId);
}
