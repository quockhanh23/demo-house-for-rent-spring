package com.example.testspringweb.repository;

import com.example.testspringweb.models.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    @Query(value = "select * from house order by created_at desc", nativeQuery = true)
    Page<House> getAllHousePage(Pageable pageable);
}
