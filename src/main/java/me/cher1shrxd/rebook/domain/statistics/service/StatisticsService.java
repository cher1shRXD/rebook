package me.cher1shrxd.rebook.domain.statistics.service;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.rebook.domain.statistics.dto.MonthlyReadCountResponse;
import me.cher1shrxd.rebook.domain.statistics.repository.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public MonthlyReadCountResponse getMonthlyReadCount() {

        LocalDateTime start = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1);

        Long count = statisticsRepository.countByCreateAtBetween(start, end);

        return new MonthlyReadCountResponse(count);
    }
}
