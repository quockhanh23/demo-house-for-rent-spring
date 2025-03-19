package com.example.testspringweb.services;

import com.example.testspringweb.models.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HouseService {

    Page<House> getAllHousePage(Pageable pageable);

    House getDetailHouse(Long houseId);

    House createHouse(House house);

    House updateHouse(House house, Long houseId);

    House updateHouseStatus(Long houseId, String status);
}
