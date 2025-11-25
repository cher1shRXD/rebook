package me.cher1shrxd.rebook.domain.book.controller;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.rebook.domain.book.dto.BookResponse;
import me.cher1shrxd.rebook.domain.book.dto.CreateBookReq;
import me.cher1shrxd.rebook.domain.book.dto.UpdateBookReq;
import me.cher1shrxd.rebook.domain.book.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/my")
    public ResponseEntity<List<BookResponse>> getMyBook() {
        return ResponseEntity.ok().body(bookService.findMyBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long id) {
        return ResponseEntity.ok().body(bookService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody CreateBookReq request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @RequestBody UpdateBookReq request) {
        return ResponseEntity.ok().body(bookService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
