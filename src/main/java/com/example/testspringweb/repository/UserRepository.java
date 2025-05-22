package com.example.testspringweb.repository;

import com.example.testspringweb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsernameAndPassword(String username, String password);

    User findUserByUsername(String username);

    @Query(value = "select * from user where (username = :searchText) or (full_name = :searchText)", nativeQuery = true)
    List<User> findAllUser(@Param("searchText") String searchText);
}
