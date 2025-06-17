package com.example.testspringweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTORequest {
    private Long id;
    private String password;
    private String newPassword;
    private String confirmNewPassword;
    private String phone;
    private String email;
    private String fullName;
}
