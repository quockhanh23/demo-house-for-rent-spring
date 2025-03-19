package com.example.testspringweb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class Transactional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long houseId;
    @NotNull
    private Long idUserHost;
    @NotNull
    private Long idUserGuest;
    @Column(length = 20)
    private String status;
    private Date createdAt;
    private Date updatedAt;
    @NotNull
    private Date startTime;
    @NotNull
    private Date endTime;
    private Date checkInTime;
    private Date checkOutTime;
    private BigDecimal totalAmount;
}
