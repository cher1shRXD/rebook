package me.cher1shrxd.rebook.domain.review.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.rebook.domain.book.entity.BookEntity;
import me.cher1shrxd.rebook.domain.book.repository.BookRepository;
import me.cher1shrxd.rebook.domain.review.dto.AddReviewRequest;
import me.cher1shrxd.rebook.domain.review.dto.ReviewResponse;
import me.cher1shrxd.rebook.domain.review.dto.UpdateReviewRequest;
import me.cher1shrxd.rebook.domain.review.entity.ReviewEntity;
import me.cher1shrxd.rebook.domain.review.repository.ReviewRepository;
import me.cher1shrxd.rebook.domain.user.entity.UserEntity;
import me.cher1shrxd.rebook.domain.user.repository.UserRepository;
import me.cher1shrxd.rebook.global.exception.CustomErrorCode;
import me.cher1shrxd.rebook.global.exception.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    public ReviewEntity save(AddReviewRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BookEntity book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return reviewRepository.save(request.toEntity(user,book));
    }

    public List<ReviewResponse> findMyReviews() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!userRepository.existsByEmail(email)) throw new CustomException(CustomErrorCode.USER_NOT_FOUND);

        return reviewRepository.findByUserEmail(email)
                .stream()
                .map(ReviewResponse::new)
                .toList();
    }

    public ReviewResponse update(Long id, UpdateReviewRequest request) {
        ReviewEntity review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
        review.updateReview(request.getTitle(), request.getContent(), request.getRating());
        return new ReviewResponse(review);
    }

    public void delete(Long id) {
        ReviewEntity review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
        reviewRepository.delete(review);
    }
}
