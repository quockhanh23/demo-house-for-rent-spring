package com.example.testspringweb.services.impl;

import com.example.testspringweb.common.CommonConstant;
import com.example.testspringweb.common.HouseConstant;
import com.example.testspringweb.dto.CountAddress;
import com.example.testspringweb.exption.InvalidException;
import com.example.testspringweb.models.Category;
import com.example.testspringweb.models.House;
import com.example.testspringweb.models.User;
import com.example.testspringweb.repository.CategoryRepository;
import com.example.testspringweb.repository.HouseRepository;
import com.example.testspringweb.repository.UserRepository;
import com.example.testspringweb.services.HouseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<House> getAllHousePage(Pageable pageable, String searchText) {
        if (StringUtils.isEmpty(searchText)) {
            return houseRepository.getAllHousePage(pageable);
        } else {
            searchText = searchText.trim();
            return houseRepository.getAllHousePage(pageable, searchText);
        }
    }

    @Override
    public Page<House> getAllHousePageByDistrict(Pageable pageable, String district) {
        district = district.trim();
        return houseRepository.getAllHousePageByDistrict(pageable, district);
    }

    @Override
    public Page<House> getAllHouseOfUser(Pageable pageable, Long idUser) {
        return houseRepository.getAllHouseOfUser(pageable, idUser);
    }

    @Override
    public House getDetailHouse(Long idHouse) {
        Optional<House> houseOptional = houseRepository.findById(idHouse);
        if (houseOptional.isEmpty()) {
            throw new InvalidException("Invalid house");
        }
        return houseOptional.get();
    }

    @Override
    public List<House> getAllHouseByDistrict(String district) {
        List<House> houseList = houseRepository.getAllByDistrict(district);
        if (CollectionUtils.isEmpty(houseList)) {
            houseList = new ArrayList<>();
        }
        if (houseList.size() > 10) {
            houseList = houseList.subList(0, 10);
        }
        return houseList;
    }

    @Override
    public House createHouse(House houseRequest) {
        Long idCategory = houseRequest.getCategoryId();
        Long idUser = houseRequest.getIdUser();
        Optional<Category> categoryOptional = categoryRepository.findById(idCategory);
        if (categoryOptional.isEmpty()) {
            throw new InvalidException("Invalid category.sql");
        }
        Optional<User> userOptional = userRepository.findById(idUser);
        if (userOptional.isEmpty()) {
            throw new InvalidException("Invalid user");
        }
        houseRequest.setUsername(userOptional.get().getUsername());
        houseRequest.setCreatedAt(new Date());
        houseRequest.setStatus(CommonConstant.ACTIVE);
        return houseRepository.save(houseRequest);
    }

    @Override
    public House updateHouse(House house, Long idHouse) {
        getDetailHouse(idHouse);
        house.setUpdatedAt(new Date());
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

    @Override
    public List<CountAddress> getAllWardByDistrictAndCount(String sameAddress) {
        List<CountAddress> countAddresses = houseRepository.getAllWardByDistrictAndCount(sameAddress);
        if (CollectionUtils.isEmpty(countAddresses)) countAddresses = new ArrayList<>();
        return countAddresses;
    }

    @Override
    public List<CountAddress> getAllDistrictAndCount() {
        List<CountAddress> countAddresses = houseRepository.getAllDistrictAndCount();
        if (CollectionUtils.isEmpty(countAddresses)) countAddresses = new ArrayList<>();
        return countAddresses;
    }

    @Override
    public List<House> getFiveMostExpensive() {
        List<House> houseList = houseRepository.getFiveMostExpensive();
        if (CollectionUtils.isEmpty(houseList)) houseList = new ArrayList<>();
        return houseList;
    }
}
