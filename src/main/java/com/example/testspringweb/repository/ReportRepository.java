package com.example.testspringweb.repository;

import com.example.testspringweb.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> getAllByIdHouse(Long idHouse);

    int countAllByIdHouse(Long idHouse);
}
