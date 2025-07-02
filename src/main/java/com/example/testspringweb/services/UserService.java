package com.example.testspringweb.services;

import com.example.testspringweb.dto.UserDTORequest;
import com.example.testspringweb.dto.UserDTOResponse;
import com.example.testspringweb.models.User;
import com.example.testspringweb.models.UserPrinciple;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDTOResponse register(User user);

    UserDTOResponse updateUser(UserDTORequest userDTORequest);

    void changePassword(UserDTORequest userDTORequest);

    void validateChangePassword(UserDTORequest userDTORequest);

    UserDTOResponse getDetailUser(Long userId);

    UserPrinciple loadUserByUsername(String username);

    void checkUserInActive(UserPrinciple userPrinciple);

    List<UserDTOResponse> getAllUser(Long idAdmin, String searchText);

    void actionUser(Long idAdmin, String action, Long idUser);

    void checkAdmin(Long idAdmin);
}

