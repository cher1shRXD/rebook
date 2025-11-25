package me.cher1shrxd.rebook.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.cher1shrxd.rebook.domain.review.entity.ReviewEntity;
import me.cher1shrxd.rebook.domain.user.entity.UserEntity;
import org.apache.catalina.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddReviewRequest {
    String title;
    String content;
    Long bookId;

    public ReviewEntity toEntity(UserEntity user) {
        return ReviewEntity.builder()
                .user(user)
                .title(title)
                .content(content)
                .bookId(bookId)
                .build();
    }
}
