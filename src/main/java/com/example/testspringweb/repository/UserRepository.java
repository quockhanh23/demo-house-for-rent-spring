package com.example.testspringweb.repository;

import com.example.testspringweb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsernameAndPassword(String username, String password);
}
