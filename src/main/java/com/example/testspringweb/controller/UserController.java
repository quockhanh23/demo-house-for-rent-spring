package com.example.testspringweb.controller;

import com.example.testspringweb.auth.JWTService;
import com.example.testspringweb.auth.JwtResponse;
import com.example.testspringweb.dto.LoginRequest;
import com.example.testspringweb.dto.UserDTORequest;
import com.example.testspringweb.dto.UserDTOResponse;
import com.example.testspringweb.models.Role;
import com.example.testspringweb.models.User;
import com.example.testspringweb.models.UserPrinciple;
import com.example.testspringweb.repository.RoleRepository;
import com.example.testspringweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        Set<Role> roles = new HashSet<>();
        Role role = (user.getRoles() != null) ?
                roleRepository.findByName("ADMIN") : roleRepository.findByName("USER");
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.register(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Object> updateInformation(@RequestBody UserDTORequest dtoRequest) {
        UserDTOResponse userDTOResponse = userService.updateUser(dtoRequest);
        return new ResponseEntity<>(userDTOResponse, HttpStatus.OK);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody UserDTORequest dtoRequest) {
        userService.validateChangePassword(dtoRequest);
        dtoRequest.setPassword(passwordEncoder.encode(dtoRequest.getPassword()));
        dtoRequest.setPassword(passwordEncoder.encode(dtoRequest.getConfirmNewPassword()));
        userService.changePassword(dtoRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/getDetailUser")
    public ResponseEntity<Object> getDetailUser(@RequestParam Long idUser) {
        UserDTOResponse userDTOResponse = userService.getDetailUser(idUser);
        return new ResponseEntity<>(userDTOResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequest loginRequest, BindingResult errors) {
        if (errors.hasErrors()) {
            Map<String, String> map = new HashMap<>();
            List<FieldError> list = errors.getFieldErrors();
            for (FieldError fieldError : list) {
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        try {
            Authentication authentication = authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrinciple userPrinciple = userService.loadUserByUsername(loginRequest.getUsername());
            String jwt = jwtService.generateToken(userPrinciple);
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setId(userPrinciple.getId());
            jwtResponse.setToken(jwt);
            jwtResponse.setRoles(userPrinciple.getAuthorities());
            jwtResponse.setUsername(userPrinciple.getUsername());
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
