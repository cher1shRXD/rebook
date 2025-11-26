package me.cher1shrxd.rebook.domain.statistics.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.rebook.domain.book.dto.BookResponse;
import me.cher1shrxd.rebook.domain.book.entity.BookEntity;
import me.cher1shrxd.rebook.domain.book.repository.BookRepository;
import me.cher1shrxd.rebook.domain.statistics.dto.MonthlyReadCountResponse;
import me.cher1shrxd.rebook.domain.statistics.dto.TopRatedBooksResponse;
import me.cher1shrxd.rebook.domain.statistics.repository.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final BookRepository bookRepository;

    public MonthlyReadCountResponse getMonthlyReadCount() {
        LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1);

        Long count = statisticsRepository.countByCreateAtBetween(start, end);

        return new MonthlyReadCountResponse(count);
    }

    public TopRatedBooksResponse getTopRatedBooks() {
        List<Object[]> result = statisticsRepository.findBooksOrderByAverageRating();
        List<BookResponse> books = result.stream()
                .limit(5)
                .map(row -> (Long) row[0])
                .map(bookRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(BookResponse::new)
                .toList();
        return new TopRatedBooksResponse(books);
    }
}
