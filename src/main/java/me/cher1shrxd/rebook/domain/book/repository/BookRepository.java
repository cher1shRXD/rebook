package me.cher1shrxd.rebook.domain.book.repository;

import me.cher1shrxd.rebook.domain.book.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findAllByAddedBy_Id(Long addedById);

    List<BookEntity> findAllByAddedBy_Email(String addedByEmail);

    BookEntity findByIdAndAddedBy_Email(Long id, String addedByEmail);
}
