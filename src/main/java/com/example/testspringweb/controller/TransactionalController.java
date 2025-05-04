package com.example.testspringweb.controller;

import com.example.testspringweb.models.Transactional;
import com.example.testspringweb.services.CommonService;
import com.example.testspringweb.services.TransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/transactions")
public class TransactionalController {

    @Autowired
    private TransactionalService transactionalService;

    private final CommonService commonService = new CommonService();

    @GetMapping("/getAllTransactionalPage")
    public ResponseEntity<Object> getAllTransactionalPage(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transactional> housePage = transactionalService.getAllTransactionalPage(pageable);
        return new ResponseEntity<>(housePage, HttpStatus.OK);
    }

    // Lịch sử giao dịch của 1 ngôi nhà
    @GetMapping("/getAllTransactionalPageByHouseId")
    public ResponseEntity<Object> getAllTransactionalPage(
            @RequestParam Long houseId,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transactional> housePage = transactionalService.getAllTransactionalByHouseId(houseId, pageable);
        return new ResponseEntity<>(housePage, HttpStatus.OK);
    }

    @GetMapping("/getDetailTransactional")
    public ResponseEntity<Object> getDetailTransactional(@RequestParam Long transactionalId) {
        Transactional transactional = transactionalService.getDetailTransactional(transactionalId);
        return new ResponseEntity<>(transactional, HttpStatus.OK);
    }

    // Tổng thu nhập theo tháng
    @GetMapping("/totalMonthly")
    public ResponseEntity<Object> totalMonthly(@RequestParam Long userId, @RequestParam String month) {
        try {
            return new ResponseEntity<>(transactionalService.totalMonthly(userId, month), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createTransactional")
    public ResponseEntity<Object> createTransactional(
            @RequestBody @Valid Transactional transactional, BindingResult errors) {
        Map<String, String> validate = commonService.validateInput(errors);
        if (Objects.nonNull(validate)) {
            return new ResponseEntity<>(validate, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(transactionalService.createTransactional(transactional), HttpStatus.CREATED);
    }

    // Hủy thuê nhà trước 1 ngày
    @PostMapping("/cancelRental")
    public ResponseEntity<Object> cancelRental(@RequestParam Long transactionalId) {
        return new ResponseEntity<>(transactionalService.cancelRental(transactionalId), HttpStatus.OK);
    }

    @PutMapping("/updateTransactional")
    public ResponseEntity<Object> updateTransactional(@RequestParam Long transactionalId, @RequestParam String status) {
        try {
            Transactional transactional = transactionalService.updateTransactional(transactionalId, status);
            return new ResponseEntity<>(transactional, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/checkIn")
    public ResponseEntity<Object> checkIn(@RequestParam Long transactionalId, @RequestParam Long userId) {
        Transactional transactional = transactionalService.checkIn(transactionalId, userId);
        return new ResponseEntity<>(transactional, HttpStatus.OK);
    }
}
