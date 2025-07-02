package com.example.testspringweb.controller;

import com.example.testspringweb.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/getAllReviewByIdHouse")
    public ResponseEntity<Object> getAllReviewByIdHouse(@RequestParam Long houseId) {
        return new ResponseEntity<>(reviewService.getAllReviewByHouseId(houseId), HttpStatus.OK);
    }

    @PostMapping("/createReview")
    public ResponseEntity<Object> createReview(
            @RequestParam Long idUser, @RequestParam Long isHouse, @RequestParam int rate) {
        boolean checkReview = reviewService.createReview(idUser, isHouse, rate);
        return new ResponseEntity<>(checkReview, HttpStatus.CREATED);
    }

    @PostMapping("/updateReview")
    public ResponseEntity<Object> updateReview(
            @RequestParam Long idUser, @RequestParam Long isHouse, @RequestParam int rate) {
        boolean checkReview = reviewService.updateReview(idUser, isHouse, rate);
        return new ResponseEntity<>(checkReview, HttpStatus.OK);
    }

    @PostMapping("/updateStatusReview")
    public ResponseEntity<Object> updateStatusReview(
            @RequestParam Long idReview, @RequestParam Long idUser, @RequestParam String status) {
        boolean checkReview = reviewService.updateStatusReview(idReview, idUser, status);
        return new ResponseEntity<>(checkReview, HttpStatus.OK);
    }
}
