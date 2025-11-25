package me.cher1shrxd.rebook.domain.review.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.rebook.domain.review.dto.AddReviewRequest;
import me.cher1shrxd.rebook.domain.review.entity.ReviewEntity;
import me.cher1shrxd.rebook.domain.review.repository.ReviewRepository;
import me.cher1shrxd.rebook.domain.user.entity.UserEntity;
import me.cher1shrxd.rebook.domain.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReviewEntity save(AddReviewRequest review) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return reviewRepository.save(review.toEntity(user));
    }

    public ReviewEntity getById(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();


    }
}
