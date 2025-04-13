package com.example.testspringweb.repository;

import com.example.testspringweb.dto.CountAddress;
import com.example.testspringweb.models.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    @Query(value = "select * from house order by created_at desc", nativeQuery = true)
    Page<House> getAllHousePage(Pageable pageable);

    List<House> getAllByAddress(String address);

    @Query("SELECT new com.example.testspringweb.dto.CountAddress(h.ward, COUNT(h)) FROM House h WHERE h.district = :district GROUP BY h.ward")
    List<CountAddress> getAllByDistrictAndCount(String district);
}


