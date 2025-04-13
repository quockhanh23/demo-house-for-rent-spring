package com.example.testspringweb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTORequest {
    private Long id;
    private String password;
    private String newPassword;
    private String confirmNewPassword;
    private String phone;
    private String email;
    private String fullName;
}
