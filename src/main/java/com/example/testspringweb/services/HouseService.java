package com.example.testspringweb.services;

import com.example.testspringweb.dto.CountAddress;
import com.example.testspringweb.models.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HouseService {

    Page<House> getAllHousePage(Pageable pageable, String searchText);

    Page<House> getAllHousePageByDistrict(Pageable pageable, String district);

    Page<House> getAllHouseOfUser(Pageable pageable, Long idUser);

    House getDetailHouse(Long idHouse);

    List<House> getAllHouseByDistrict(String address);

    House createHouse(House house);

    House updateHouse(House house, Long idHouse);

    House updateHouseStatus(Long houseId, String status);

    List<CountAddress> getAllWardByDistrictAndCount(String sameAddress);

    List<CountAddress> getAllDistrictAndCount();

    List<House> getFiveMostExpensive();

}
