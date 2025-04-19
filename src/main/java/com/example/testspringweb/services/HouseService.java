package com.example.testspringweb.services;

import com.example.testspringweb.dto.CountAddress;
import com.example.testspringweb.models.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HouseService {

    Page<House> getAllHousePage(Pageable pageable, String searchText);

    House getDetailHouse(Long houseId);

    List<House> getAllHouseByAddress(String address);

    House createHouse(House house);

    House updateHouse(House house, Long houseId);

    House updateHouseStatus(Long houseId, String status);

    List<CountAddress> getAllWardByDistrictAndCount(String sameAddress);

    List<CountAddress> getAllDistrictAndCount();

    List<House> getFiveMostExpensive();

}
