package com.example.testspringweb.controller;

import com.example.testspringweb.dto.CountAddress;
import com.example.testspringweb.models.House;
import com.example.testspringweb.services.CommonService;
import com.example.testspringweb.services.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/houses")
public class HouseController {

    @Autowired
    private HouseService houseService;

    private final CommonService commonService = new CommonService();

    @GetMapping("/getAllPage")
    public ResponseEntity<Object> getAllHousePage(@RequestParam(defaultValue = "0", required = false) int page,
                                                  @RequestParam(defaultValue = "10", required = false) int size,
                                                  @RequestParam(required = false) String searchText) {
        Pageable pageable = PageRequest.of(page, size);
        Page<House> housePage = houseService.getAllHousePage(pageable, searchText);
        return new ResponseEntity<>(housePage, HttpStatus.OK);
    }

    @GetMapping("/getAllHousePageByDistrict")
    public ResponseEntity<Object> getAllHousePageByDistrict(@RequestParam(defaultValue = "0", required = false) int page,
                                                            @RequestParam(defaultValue = "10", required = false) int size,
                                                            @RequestParam(required = false) String searchText) {
        Pageable pageable = PageRequest.of(page, size);
        Page<House> housePage = houseService.getAllHousePageByDistrict(pageable, searchText);
        return new ResponseEntity<>(housePage, HttpStatus.OK);
    }

    @GetMapping("/getAllHouseOfUser")
    public ResponseEntity<Object> getAllHouseOfUser(@RequestParam(defaultValue = "0", required = false) int page,
                                                    @RequestParam(defaultValue = "10", required = false) int size,
                                                    @RequestParam Long idUser) {
        Pageable pageable = PageRequest.of(page, size);
        Page<House> housePage = houseService.getAllHouseOfUser(pageable, idUser);
        return new ResponseEntity<>(housePage, HttpStatus.OK);
    }

    @GetMapping("/getAllHouseByDistrict")
    public ResponseEntity<Object> getAllHouseByDistrict(@RequestParam String district) {
        List<House> house = houseService.getAllHouseByDistrict(district);
        return new ResponseEntity<>(house, HttpStatus.OK);
    }

    @GetMapping("/getDetailHouse")
    public ResponseEntity<Object> getDetailHouse(@RequestParam Long idHouse) {
        House house = houseService.getDetailHouse(idHouse);
        return new ResponseEntity<>(house, HttpStatus.OK);
    }

    @GetMapping("/getAllWardByDistrictAndCount")
    public ResponseEntity<Object> getAllWardByDistrictAndCount(@RequestParam String address) {
        List<CountAddress> house = houseService.getAllWardByDistrictAndCount(address);
        return new ResponseEntity<>(house, HttpStatus.OK);
    }

    @GetMapping("/getAllDistrictAndCount")
    public ResponseEntity<Object> getAllDistrictAndCount() {
        List<CountAddress> house = houseService.getAllDistrictAndCount();
        return new ResponseEntity<>(house, HttpStatus.OK);
    }

    @GetMapping("/topMostExpensive")
    public ResponseEntity<Object> topMostExpensive() {
        List<House> house = houseService.getFiveMostExpensive();
        return new ResponseEntity<>(house, HttpStatus.OK);
    }

    @PostMapping("/createHouse")
    public ResponseEntity<Object> createHouse(@RequestBody @Valid House house, BindingResult errors) {
        Map<String, String> validate = commonService.validateInput(errors);
        if (Objects.nonNull(validate)) {
            return new ResponseEntity<>(validate, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(houseService.createHouse(house), HttpStatus.CREATED);
    }

    @PutMapping("/updateHouse")
    public ResponseEntity<Object> updateHouse(@RequestBody @Valid House houseRequest,
                                              @RequestParam Long idHouse, BindingResult errors) {
        Map<String, String> validate = commonService.validateInput(errors);
        if (Objects.nonNull(validate)) {
            return new ResponseEntity<>(validate, HttpStatus.BAD_REQUEST);
        }
        House house = houseService.updateHouse(houseRequest, idHouse);
        return new ResponseEntity<>(house, HttpStatus.OK);
    }

    @PutMapping("/updateStatusHouse")
    public ResponseEntity<Object> updateStatusHouse(@RequestParam Long idHouse, @RequestParam String status) {
        House house = houseService.updateHouseStatus(idHouse, status);
        return new ResponseEntity<>(house, HttpStatus.OK);
    }
}
