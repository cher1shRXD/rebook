package me.cher1shrxd.rebook.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import me.cher1shrxd.rebook.domain.user.entity.UserEntity;

@Entity
@Getter
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
