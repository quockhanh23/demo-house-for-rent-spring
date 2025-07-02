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

    @Query(value = "select * from house", nativeQuery = true)
    Page<House> getAllHousePage(Pageable pageable);

    @Query(value = "select * from house WHERE province like CONCAT('%', :searchText, '%') or district like CONCAT('%', :searchText, '%') or address like CONCAT('%', :searchText, '%') or price like CONCAT('%', :searchText, '%')", nativeQuery = true)
    Page<House> getAllHousePage(Pageable pageable, String searchText);

    @Query(value = "select * from house WHERE district like CONCAT('%', :searchText, '%') order by created_at desc", nativeQuery = true)
    Page<House> getAllHousePageByDistrict(Pageable pageable, String searchText);

    @Query(value = "select * from house WHERE id_user = :idUser order by created_at desc", nativeQuery = true)
    Page<House> getAllHouseOfUser(Pageable pageable, Long idUser);

    List<House> getAllByDistrict(String district);

    @Query("SELECT new com.example.testspringweb.dto.CountAddress(h.ward, '', COUNT(h)) FROM House h WHERE h.district = :district GROUP BY h.ward")
    List<CountAddress> getAllWardByDistrictAndCount(String district);

    @Query("SELECT new com.example.testspringweb.dto.CountAddress('', h.district, COUNT(h)) FROM House h GROUP BY h.district")
    List<CountAddress> getAllDistrictAndCount();

    @Query(value = "select * from house order by price desc limit 5", nativeQuery = true)
    List<House> getFiveMostExpensive();

    List<House> getAllByIdIn(List<Long> idList);
}


