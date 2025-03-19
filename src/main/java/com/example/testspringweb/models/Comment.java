package com.example.testspringweb.models;

import jakarta.persistence.*;
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
    private String content;
    private Long idUser;
    private Long idHouse;
    private Date createdAt;
    private Date updatedAt;
    @Column(length = 20)
    private String status;
}
