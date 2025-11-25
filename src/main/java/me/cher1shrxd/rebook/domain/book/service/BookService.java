package me.cher1shrxd.rebook.domain.book.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.rebook.domain.book.dto.BookResponse;
import me.cher1shrxd.rebook.domain.book.dto.CreateBookReq;
import me.cher1shrxd.rebook.domain.book.dto.UpdateBookReq;
import me.cher1shrxd.rebook.domain.book.entity.BookEntity;
import me.cher1shrxd.rebook.domain.book.repository.BookRepository;
import me.cher1shrxd.rebook.domain.user.entity.UserEntity;
import me.cher1shrxd.rebook.domain.user.repository.UserRepository;
import me.cher1shrxd.rebook.global.exception.CustomErrorCode;
import me.cher1shrxd.rebook.global.exception.CustomException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public List<BookResponse> findMyBooks() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!userRepository.existsByEmail(email)) throw new CustomException(CustomErrorCode.USER_NOT_FOUND);

        List<BookEntity> result = bookRepository.findAllByAddedBy_Email(email);

        return result.stream().map(BookResponse::new).toList();
    }

    public BookResponse findById(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!userRepository.existsByEmail(email)) throw new CustomException(CustomErrorCode.USER_NOT_FOUND);

        BookEntity result = bookRepository.findById(id).orElseThrow(() -> new CustomException(CustomErrorCode.BOOK_NOT_FOUND));

        if(!result.getAddedBy().getEmail().equals(email)) throw new CustomException(CustomErrorCode.BOOK_FORBIDDEN);

        return new BookResponse(result);
    }

    public BookResponse save(CreateBookReq request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        BookEntity newBook = BookEntity.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .addedBy(userEntity)
                .build();

        return new BookResponse(bookRepository.save(newBook));
    }

    @Transactional
    public BookResponse update(Long id, UpdateBookReq request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!userRepository.existsByEmail(email)) throw new CustomException(CustomErrorCode.USER_NOT_FOUND);

        BookEntity target = bookRepository.findById(id).orElseThrow(() -> new CustomException(CustomErrorCode.BOOK_NOT_FOUND));

        if(!target.getAddedBy().getEmail().equals(email)) throw new CustomException(CustomErrorCode.BOOK_FORBIDDEN);

        target.updateBook(request.getTitle(), request.getAuthor());

        return new BookResponse(target);
    }

    public void delete(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!userRepository.existsByEmail(email)) throw new CustomException(CustomErrorCode.USER_NOT_FOUND);

        BookEntity target = bookRepository.findById(id).orElseThrow(() -> new CustomException(CustomErrorCode.BOOK_NOT_FOUND));

        if(!target.getAddedBy().getEmail().equals(email)) throw new CustomException(CustomErrorCode.BOOK_FORBIDDEN);

        bookRepository.delete(target);
    }
}
