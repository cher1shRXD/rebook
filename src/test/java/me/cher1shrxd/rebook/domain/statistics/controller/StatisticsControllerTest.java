package me.cher1shrxd.rebook.domain.statistics.controller;

import me.cher1shrxd.rebook.domain.statistics.dto.MonthlyReadCountResponse;
import me.cher1shrxd.rebook.domain.statistics.dto.TopRatedBooksResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("월별 독서 수 조회 테스트")
    void getMonthlyReadCountTest() throws Exception {
        mockMvc.perform(get("/statistics/monthly")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monthlyCounts").exists());
    }

    @Test
    @DisplayName("평점 상위 책 조회 테스트")
    void getTopRatedBooksTest() throws Exception {
        mockMvc.perform(get("/statistics/top-rated")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topBooks").exists());
    }
}
