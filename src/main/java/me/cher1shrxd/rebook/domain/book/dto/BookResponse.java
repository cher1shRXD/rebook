package me.cher1shrxd.rebook.domain.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.cher1shrxd.rebook.domain.book.entity.BookEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String author;

    public BookResponse(BookEntity bookEntity){
        this.id = bookEntity.getId();
        this.title = bookEntity.getTitle();
        this.author = bookEntity.getAuthor();
    }
}
