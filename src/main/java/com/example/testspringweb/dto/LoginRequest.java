package com.example.testspringweb.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class LoginRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
