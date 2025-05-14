package com.example.testspringweb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 500)
    @NotNull
    private String content;
    @NotNull
    private Long idUser;
    private String username;
    @NotNull
    private Long idHouse;
    private Date createdAt;
    private Date updatedAt;
    @Column(length = 20)
    private String status;
}
