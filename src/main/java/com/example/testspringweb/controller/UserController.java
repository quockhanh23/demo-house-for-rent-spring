package com.example.testspringweb.controller;

import com.example.testspringweb.dto.LoginRequest;
import com.example.testspringweb.dto.UserDTORequest;
import com.example.testspringweb.dto.UserDTOResponse;
import com.example.testspringweb.models.User;
import com.example.testspringweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    ResponseEntity<Object> registerUser(@RequestBody User user) {
        UserDTOResponse userDTOResponse = userService.register(user);
        return new ResponseEntity<>(userDTOResponse, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    ResponseEntity<Object> updateInformation(@RequestBody UserDTORequest dtoRequest) {
        UserDTOResponse userDTOResponse = userService.updateUser(dtoRequest);
        return new ResponseEntity<>(userDTOResponse, HttpStatus.OK);
    }

    @GetMapping("/getDetailUser")
    public ResponseEntity<Object> getDetailUser(@RequestParam Long idUser) {
        UserDTOResponse userDTOResponse = userService.getDetailUser(idUser);
        return new ResponseEntity<>(userDTOResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    ResponseEntity<Object> login(@RequestBody @Valid LoginRequest loginRequest, BindingResult errors) {
        if (errors.hasErrors()) {
            Map<String, String> map = new HashMap<>();
            List<FieldError> list = errors.getFieldErrors();
            for (FieldError fieldError : list) {
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.checkLogin(loginRequest), HttpStatus.OK);
    }
}
