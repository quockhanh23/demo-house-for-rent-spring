package com.example.testspringweb.controller;

import com.example.testspringweb.dto.UserDTO;
import com.example.testspringweb.models.User;
import com.example.testspringweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    ResponseEntity<Object> registerUser(@RequestParam User user) {
        UserDTO userDTO = userService.register(user);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }
}
