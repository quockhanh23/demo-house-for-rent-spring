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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rate;
    private Long idUser;
    private Long idHouse;
    private Date createdAt;
    private Date updatedAt;
    @Column(length = 20)
    private String status;
}
