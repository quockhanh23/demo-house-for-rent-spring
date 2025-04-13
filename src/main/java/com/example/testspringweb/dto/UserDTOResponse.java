package com.example.testspringweb.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDTOResponse {
    private Long id;
    private String uuid;
    private String username;
    private String fullName;
    private String phone;
    private String email;
    private Date createdAt;
}
