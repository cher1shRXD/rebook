package me.cher1shrxd.rebook.domain.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.cher1shrxd.rebook.domain.book.dto.BookResponse;

import java.util.List;

@Getter
@AllArgsConstructor
public class TopRatedBooksResponse {
    private List<BookResponse> topBooks;
}
