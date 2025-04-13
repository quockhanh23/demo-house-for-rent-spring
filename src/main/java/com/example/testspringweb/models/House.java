package com.example.testspringweb.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String name;
    @Column(length = 500)
    private String address;
    private String province;
    private String district;
    private String ward;
    private Long categoryId;
    @Column(length = 100)
    @NotNull
    @Size(min = 5, max = 100)
    private String title;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer numberOfBedrooms;
    @NotNull
    private Integer numberOfBathrooms;
    private Boolean withGarden;
    @NotNull
    private Long idUser;
    private String username;
    private Date createdAt;
    private Date updatedAt;
    @Column(length = 20)
    private String status;
    @Lob
    private String image;
    private Integer acreage;
    private String description;
}
