package me.cher1shrxd.rebook.domain.statistics.repository;

import me.cher1shrxd.rebook.domain.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<ReviewEntity, Long> {

    Long countByCreateAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
        SELECT r.book.id, AVG(r.rating) AS avgRating
        FROM ReviewEntity r
        GROUP BY r.book.id
        ORDER BY avgRating DESC
        """)
    List<Object[]> findBooksOrderByAverageRating();
}
