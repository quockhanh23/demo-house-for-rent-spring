package com.example.testspringweb.services.impl;

import com.example.testspringweb.common.CommonConstant;
import com.example.testspringweb.models.Review;
import com.example.testspringweb.repository.ReviewRepository;
import com.example.testspringweb.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllReviewByHouseId(Long houseId) {
        List<Review> reviews = reviewRepository.getAllByIdHouse(houseId);
        if (CollectionUtils.isEmpty(reviews)) {
            return new ArrayList<>();
        }
        return reviews;
    }

    @Override
    public boolean createReview(Long idUser, Long idHouse, int rate) {
        Review review = new Review();
        review.setCreatedAt(new Date());
        review.setStatus(CommonConstant.ACTIVE);
        review.setRate(rate);
        review.setIdUser(idUser);
        review.setIdHouse(idHouse);
        reviewRepository.save(review);
        return true;
    }

    @Override
    public boolean updateReview(Long idReview, Long idUser, int rate) {
        Optional<Review> optionalReview = reviewRepository.findById(idReview);
        if (optionalReview.isEmpty()) {
            return false;
        }
        if (optionalReview.get().getIdUser().equals(idUser)) {
            optionalReview.get().setRate(rate);
            optionalReview.get().setUpdatedAt(new Date());
            reviewRepository.save(optionalReview.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStatusReview(Long idReview, Long idUser, String status) {
        Optional<Review> optionalReview = reviewRepository.findById(idReview);
        if (optionalReview.isEmpty()) {
            return false;
        }
        if (optionalReview.get().getIdUser().equals(idUser)) {
            optionalReview.get().setStatus(status);
            optionalReview.get().setUpdatedAt(new Date());
            reviewRepository.save(optionalReview.get());
            return true;
        }
        return false;
    }
}
