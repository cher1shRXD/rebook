package me.cher1shrxd.rebook.domain.review.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.rebook.domain.review.dto.AddReviewRequest;
import me.cher1shrxd.rebook.domain.review.dto.ReviewResponse;
import me.cher1shrxd.rebook.domain.review.dto.UpdateReviewRequest;
import me.cher1shrxd.rebook.domain.review.entity.ReviewEntity;
import me.cher1shrxd.rebook.domain.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "REVIEW")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<ReviewEntity> createReview(@RequestBody AddReviewRequest request) {
        ReviewEntity saveReview = reviewService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveReview);
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponse>> getMyReviews() {
        return ResponseEntity.ok().body(reviewService.findMyReviews());
    }

    @PutMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long id, @RequestBody UpdateReviewRequest request) {
        ReviewResponse updateReview = reviewService.update(id, request);
        return ResponseEntity.ok().body(updateReview);
    }

    @DeleteMapping("reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
        return  ResponseEntity.ok().build();
    }

}
