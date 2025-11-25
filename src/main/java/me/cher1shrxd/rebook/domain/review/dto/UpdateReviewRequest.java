package me.cher1shrxd.rebook.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateReviewRequest {
    private String title;
    private String content;
    private Long rating;
}
