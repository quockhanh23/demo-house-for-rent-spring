package com.example.testspringweb;

import com.example.testspringweb.common.CommonConstant;
import com.example.testspringweb.models.Role;
import com.example.testspringweb.models.User;
import com.example.testspringweb.repository.RoleRepository;
import com.example.testspringweb.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootApplication
public class HouseForRentWebApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(HouseForRentWebApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public CommandLineRunner run(UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder) {
        return args ->
        {
            if (roleRepository.findByName(CommonConstant.ROLE_ADMIN) == null) {
                Role role = new Role();
                role.setName(CommonConstant.ROLE_ADMIN);
                roleRepository.save(role);
            }
            if (roleRepository.findByName(CommonConstant.ROLE_USER) == null) {
                Role role = new Role();
                role.setName(CommonConstant.ROLE_USER);
                roleRepository.save(role);
            }

            User admin = userRepository.findUserByUsername("admin");
            if (Objects.isNull(admin)) {
                User user = new User();
                Set<Role> roles = new HashSet<>();
                Role role = roleRepository.findByName(CommonConstant.ROLE_ADMIN);
                roles.add(role);
                user.setRoles(roles);
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                user.setConfirmPassword(passwordEncoder.encode("admin"));
                user.setFullName("admin");
                user.setCreatedAt(new Date());
                user.setUpdatedAt(new Date());
                user.setStatus(CommonConstant.ACTIVE);
                user.setUuid(UUID.randomUUID().toString());
                userRepository.save(user);
            }
        };
    }
}
