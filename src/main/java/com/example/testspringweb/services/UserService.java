package com.example.testspringweb.services;

import com.example.testspringweb.dto.UserDTO;
import com.example.testspringweb.models.User;

public interface UserService {

    UserDTO register(User user);
}
