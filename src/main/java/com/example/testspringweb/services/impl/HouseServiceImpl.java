package com.example.testspringweb.services.impl;

import com.example.testspringweb.common.HouseConstant;
import com.example.testspringweb.exption.InvalidException;
import com.example.testspringweb.models.Category;
import com.example.testspringweb.models.House;
import com.example.testspringweb.models.User;
import com.example.testspringweb.repository.CategoryRepository;
import com.example.testspringweb.repository.HouseRepository;
import com.example.testspringweb.repository.UserRepository;
import com.example.testspringweb.services.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<House> getAllHousePage(Pageable pageable) {
        return houseRepository.getAllHousePage(pageable);
    }

    @Override
    public House getDetailHouse(Long houseId) {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if (houseOptional.isEmpty()) {
            throw new InvalidException("Invalid house");
        }
        return houseOptional.get();
    }

    @Override
    public House createHouse(House houseRequest) {
        Long idCategory = houseRequest.getCategoryId();
        Long idUser = houseRequest.getIdUser();
        Optional<Category> categoryOptional = categoryRepository.findById(idCategory);
        if (categoryOptional.isEmpty()) {
            throw new InvalidException("Invalid category");
        }
        Optional<User> userOptional = userRepository.findById(idUser);
        if (userOptional.isEmpty()) {
            throw new InvalidException("Invalid user");
        }
        return houseRepository.save(houseRequest);
    }

    @Override
    public House updateHouse(House house, Long houseId) {
        getDetailHouse(houseId);
        return houseRepository.save(house);
    }

    @Override
    public House updateHouseStatus(Long houseId, String status) {
        House house = getDetailHouse(houseId);
        List<String> listStatus = Arrays.asList(HouseConstant.ACTIVE, HouseConstant.INACTIVE, HouseConstant.PENDING);
        if (!listStatus.contains(status)) {
            throw new InvalidException("Invalid status");
        }
        house.setStatus(status);
        house.setUpdatedAt(new Date());
        return houseRepository.save(house);
    }
}
