package com.example.testspringweb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTOResponse {
    private Long id;
    private String uuid;
    private String username;
    private String phone;
    private String email;
}
