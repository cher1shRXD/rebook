package me.cher1shrxd.rebook.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import me.cher1shrxd.rebook.domain.user.entity.UserEntity;
import me.cher1shrxd.rebook.domain.review.entity.ReviewEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "books")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity addedBy;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews = new ArrayList<>();

    @Builder
    public BookEntity(String title, String author, UserEntity addedBy) {
        this.title = title;
        this.author = author;
        this.addedBy = addedBy;
    }

    public void updateBook(String title, String author){
        this.title = title;
        this.author = author;
    }
}
