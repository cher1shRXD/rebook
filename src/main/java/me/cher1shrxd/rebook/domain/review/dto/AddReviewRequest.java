package me.cher1shrxd.rebook.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.cher1shrxd.rebook.domain.book.entity.BookEntity;
import me.cher1shrxd.rebook.domain.review.entity.ReviewEntity;
import me.cher1shrxd.rebook.domain.user.entity.UserEntity;
import org.apache.catalina.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddReviewRequest {
    String title;
    String content;
    Long rating;
    Long bookId;

    public ReviewEntity toEntity(UserEntity user, BookEntity book) {
        return ReviewEntity.builder()
                .user(user)
                .title(title)
                .content(content)
                .rating(rating)
                .book(book)
                .build();
    }
}
