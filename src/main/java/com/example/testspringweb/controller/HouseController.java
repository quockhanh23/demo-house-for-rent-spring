package com.example.testspringweb.controller;

import com.example.testspringweb.models.House;
import com.example.testspringweb.services.CommonService;
import com.example.testspringweb.services.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/houses")
public class HouseController {

    @Autowired
    private HouseService houseService;

    private final CommonService commonService = new CommonService();
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllPage")
    public ResponseEntity<Object> getAllHousePage(@RequestParam(defaultValue = "0", required = false) int page,
                                                  @RequestParam(defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<House> housePage = houseService.getAllHousePage(pageable);
        return new ResponseEntity<>(housePage, HttpStatus.OK);
    }

    @GetMapping("/getDetailHouse")
    public ResponseEntity<Object> getDetailHouse(@RequestParam Long idHouse) {
        House house = houseService.getDetailHouse(idHouse);
        return new ResponseEntity<>(house, HttpStatus.OK);
    }

    @PostMapping("/createHouse")
    public ResponseEntity<Object> createHouse(@RequestBody @Valid House house, BindingResult errors) {
        Map<String, String> validate = commonService.validateInput(errors);
        if (Objects.nonNull(validate)) {
            return new ResponseEntity<>(validate, HttpStatus.BAD_REQUEST);
        }
        houseService.createHouse(house);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/updateHouse")
    public ResponseEntity<Object> updateHouse(@RequestBody @Valid House houseRequest,
                                              @RequestParam Long idHouse, BindingResult errors) {
        Map<String, String> validate = commonService.validateInput(errors);
        if (Objects.nonNull(validate)) {
            return new ResponseEntity<>(validate, HttpStatus.BAD_REQUEST);
        }
        House house = houseService.updateHouse(houseRequest, idHouse);
        return new ResponseEntity<>(house, HttpStatus.OK);
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<Object> updateHouse(@RequestParam Long idHouse, @RequestParam String status) {
        House house = houseService.updateHouseStatus(idHouse, status);
        return new ResponseEntity<>(house, HttpStatus.OK);
    }
}
