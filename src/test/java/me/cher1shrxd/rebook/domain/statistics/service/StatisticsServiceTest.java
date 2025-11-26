package me.cher1shrxd.rebook.domain.statistics.service;

import me.cher1shrxd.rebook.domain.book.entity.BookEntity;
import me.cher1shrxd.rebook.domain.book.repository.BookRepository;
import me.cher1shrxd.rebook.domain.statistics.dto.MonthlyReadCountResponse;
import me.cher1shrxd.rebook.domain.statistics.dto.TopRatedBooksResponse;
import me.cher1shrxd.rebook.domain.statistics.repository.StatisticsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

    @Mock
    private StatisticsRepository statisticsRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    @DisplayName("월간 리뷰 수 조회 - 성공")
    void getMonthlyReadCount_Success() {
        when(statisticsRepository.countByCreateAtBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(10L);

        MonthlyReadCountResponse response = statisticsService.getMonthlyReadCount();

        assertThat(response).isNotNull();
        assertThat(response.getTotalReadCount()).isEqualTo(10L);
    }

    @Test
    @DisplayName("평점 높은 책 조회 - 성공")
    void getTopRatedBooks_Success() {
        List<Object[]> mockResults = Arrays.asList(new Object[][]{new Object[]{1L, 4.5}});
        
        BookEntity mockBook = BookEntity.builder()
                .title("Test Book")
                .author("Test Author")
                .build();

        when(statisticsRepository.findBooksOrderByAverageRating()).thenReturn(mockResults);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(mockBook));

        TopRatedBooksResponse response = statisticsService.getTopRatedBooks();

        assertThat(response).isNotNull();
        assertThat(response.getTopBooks()).hasSize(1);
        assertThat(response.getTopBooks()).first()
                .extracting("title")
                .isEqualTo("Test Book");
    }

    @Test
    @DisplayName("월간 리뷰 수 조회 - 리뷰가 없는 경우")
    void getMonthlyReadCount_NoReviews() {
        when(statisticsRepository.countByCreateAtBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0L);

        MonthlyReadCountResponse response = statisticsService.getMonthlyReadCount();

        assertThat(response).isNotNull();
        assertThat(response.getTotalReadCount()).isZero();
    }

    @Test
    @DisplayName("평점 높은 책 조회 - 빈 리스트인 경우")
    void getTopRatedBooks_EmptyList() {
        when(statisticsRepository.findBooksOrderByAverageRating()).thenReturn(List.of());

        TopRatedBooksResponse response = statisticsService.getTopRatedBooks();

        assertThat(response).isNotNull();
        assertThat(response.getTopBooks()).isEmpty();
    }

    @Test
    @DisplayName("평점 높은 책 조회 - 책을 찾을 수 없는 경우")
    void getTopRatedBooks_BookNotFound() {
        List<Object[]> mockResults = Arrays.asList(new Object[][]{new Object[]{1L, 4.5}});

        when(statisticsRepository.findBooksOrderByAverageRating()).thenReturn(mockResults);
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        TopRatedBooksResponse response = statisticsService.getTopRatedBooks();

        assertThat(response).isNotNull();
        assertThat(response.getTopBooks()).isEmpty();
    }
}