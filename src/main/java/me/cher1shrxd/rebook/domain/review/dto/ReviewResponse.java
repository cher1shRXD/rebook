package me.cher1shrxd.rebook.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.cher1shrxd.rebook.domain.review.entity.ReviewEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    String title;
    String content;
    Long rating;
    Long bookId;

    public ReviewResponse(ReviewEntity reviewEntity) {
        this.title = reviewEntity.getTitle();
        this.content = reviewEntity.getContent();
        this.rating = reviewEntity.getRating();
        this.bookId = reviewEntity.getBook().getId();
    }
}
