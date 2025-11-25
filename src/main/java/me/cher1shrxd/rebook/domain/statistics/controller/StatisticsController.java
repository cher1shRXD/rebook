package me.cher1shrxd.rebook.domain.statistics.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.rebook.domain.statistics.dto.MonthlyReadCountResponse;
import me.cher1shrxd.rebook.domain.statistics.dto.TopRatedBooksResponse;
import me.cher1shrxd.rebook.domain.statistics.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "STATISTICS")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/statistics/monthly")
    public MonthlyReadCountResponse getMonthlyReadCount() {
        return statisticsService.getMonthlyReadCount();
    }

    @GetMapping("/statistics/top-rated")
    public TopRatedBooksResponse getTopRatedBooks() {
        return statisticsService.getTopRatedBooks();
    }
}
