package me.cher1shrxd.rebook.domain.review.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.rebook.domain.review.dto.AddReviewRequest;
import me.cher1shrxd.rebook.domain.review.entity.ReviewEntity;
import me.cher1shrxd.rebook.domain.review.service.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "REVIEW")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ReviewEntity createReview(@RequestBody AddReviewRequest review) {
        return reviewService.save(review);
    }

    @GetMapping("/reviews")
    public List<ReviewEntity> getAllReviews() {
        return reviewService
    }

}
