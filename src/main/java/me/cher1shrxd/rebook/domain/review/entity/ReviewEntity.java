package me.cher1shrxd.rebook.domain.review.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.cher1shrxd.rebook.domain.book.entity.BookEntity;
import me.cher1shrxd.rebook.domain.user.entity.UserEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String content;

    @Column(nullable = false)
    Long rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Builder
    public ReviewEntity(UserEntity user, String title, String content, Long rating, BookEntity book) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.book = book;
    }

    public void updateReview( String title, String content, Long rating) {
        this.title = title;
        this.content = content;
        this.rating = rating;
    }

}
