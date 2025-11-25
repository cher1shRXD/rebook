package me.cher1shrxd.rebook.domain.statistics.repository;

import me.cher1shrxd.rebook.domain.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface StatisticsRepository extends JpaRepository<ReviewEntity, Long> {
    Long countByCreateAtBetween(LocalDateTime start, LocalDateTime end);

}
