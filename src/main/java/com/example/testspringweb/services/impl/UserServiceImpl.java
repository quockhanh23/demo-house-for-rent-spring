package com.example.testspringweb.services.impl;

import com.example.testspringweb.dto.UserDTORequest;
import com.example.testspringweb.dto.UserDTOResponse;
import com.example.testspringweb.exption.InvalidException;
import com.example.testspringweb.models.Role;
import com.example.testspringweb.models.User;
import com.example.testspringweb.models.UserPrinciple;
import com.example.testspringweb.repository.UserRepository;
import com.example.testspringweb.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTOResponse register(User userRequest) {
        userRequest.setCreatedAt(new Date());
        userRequest.setUuid(UUID.randomUUID().toString());
        User user = userRepository.save(userRequest);
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
    public void changePassword(UserDTORequest userDTORequest) {
        Optional<User> userOptional = userRepository.findById(userDTORequest.getId());
        if (userOptional.isEmpty()) {
            throw new InvalidException("Không tìm thấy người dùng");
        }
        userOptional.get().setPassword(userDTORequest.getPassword());
        userOptional.get().setConfirmPassword(userDTORequest.getConfirmNewPassword());
        userOptional.get().setUpdatedAt(new Date());
        userRepository.save(userOptional.get());
    }

    @Override
    public void validateChangePassword(UserDTORequest userDTORequest) {

    }

    @Override
    public UserDTOResponse getDetailUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new InvalidException("Không tìm thấy người dùng");
        }
        UserDTOResponse userDTOResponse = new UserDTOResponse();
        BeanUtils.copyProperties(user.get(), userDTOResponse);
        return userDTOResponse;
    }

    @Override
    public UserPrinciple loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (Objects.nonNull(user)) {
            UserPrinciple userPrinciple = new UserPrinciple();
            userPrinciple.setId(user.getId());
            userPrinciple.setUsername(user.getUsername());
            userPrinciple.setPassword(user.getPassword());

            Set<Role> roleSet = user.getRoles();
            List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();

            for (Role role : roleSet) {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getName());
                simpleGrantedAuthorityList.add(simpleGrantedAuthority);
            }
            userPrinciple.setRoles(simpleGrantedAuthorityList);
            return userPrinciple;
        }
        return null;
    }
}
