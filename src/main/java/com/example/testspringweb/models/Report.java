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
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long idUserReport;
    @NotNull
    @Column(length = 60)
    private String username;
    @NotNull
    private Long idHouse;
    @NotNull
    @Column(length = 500)
    private String content;
    @Column(length = 20)
    private String status;
    private Date createdAt;
    private Date updatedAt;
}
