package com.example.testspringweb.controller;

import com.example.testspringweb.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/getAllReviewByIdHouse")
    public ResponseEntity<Object> getAllReviewByIdHouse(@RequestParam Long houseId) {
        try {
            return new ResponseEntity<>(reviewService.getAllReviewByHouseId(houseId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createReview")
    public ResponseEntity<Object> createReview(
            @RequestParam Long idUser, @RequestParam Long isHouse, @RequestParam int rate) {
        try {
            boolean checkReview = reviewService.createReview(idUser, isHouse, rate);
            return new ResponseEntity<>(checkReview, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateReview")
    public ResponseEntity<Object> updateReview(
            @RequestParam Long idUser, @RequestParam Long isHouse, @RequestParam int rate) {
        try {
            boolean checkReview = reviewService.updateReview(idUser, isHouse, rate);
            return new ResponseEntity<>(checkReview, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateStatusReview")
    public ResponseEntity<Object> updateStatusReview(
            @RequestParam Long idReview, @RequestParam Long idUser, @RequestParam String status) {
        try {
            boolean checkReview = reviewService.updateStatusReview(idReview, idUser, status);
            return new ResponseEntity<>(checkReview, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
