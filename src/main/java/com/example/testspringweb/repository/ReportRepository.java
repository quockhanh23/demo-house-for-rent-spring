package com.example.testspringweb.repository;

import com.example.testspringweb.models.Report;
import com.example.testspringweb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> getAllByIdHouse(Long idHouse);

    int countAllByIdHouse(Long idHouse);

    @Query(value = "select * from report where (username = :searchText) or (content like CONCAT('%', :searchText, '%'))", nativeQuery = true)
    List<Report> findAllReport(@Param("searchText") String searchText);
}
