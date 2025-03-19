package com.example.testspringweb.services;

import com.example.testspringweb.models.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviewByHouseId(Long idHouse);

    boolean createReview(Long idUser, Long idHouse, int rate);

    boolean updateReview(Long idReview, Long idUser, int rate);

    boolean updateStatusReview(Long idReview, Long idUser, String status);
}
