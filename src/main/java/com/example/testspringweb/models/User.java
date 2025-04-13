package com.example.testspringweb.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    @Size(min = 3, max = 60)
    @Column(length = 60, unique = true)
    private String username;
    private String password;
    private String confirmPassword;
    @Column(length = 150)
    private String fullName;
    @Column(length = 20)
    private String phone;
    @Column(length = 30)
    private String email;
    private Date createdAt;
    private Date updatedAt;
    @Column(length = 20)
    private String status;
    @Lob
    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;
}
