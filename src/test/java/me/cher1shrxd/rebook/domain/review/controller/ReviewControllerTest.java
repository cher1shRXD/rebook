package me.cher1shrxd.rebook.domain.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.cher1shrxd.rebook.domain.review.dto.AddReviewRequest;
import me.cher1shrxd.rebook.domain.review.dto.UpdateReviewRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long testUserId = 1L;
    private Long testBookId = 1L;

    private Long reviewId; // 생성된 리뷰 ID 저장

    @BeforeEach
    void setUp() throws Exception {
        // 테스트용 리뷰 하나 미리 생성
        AddReviewRequest request = new AddReviewRequest(
                "테스트 리뷰", // title
                "좋아요",     // content
                testUserId,   // userId
                testBookId    // bookId
        );

        String response = mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        reviewId = objectMapper.readTree(response).get("id").asLong();
    }

    @Test
    @DisplayName("리뷰 생성 테스트")
    void createReviewTest() throws Exception {
        AddReviewRequest request = new AddReviewRequest(
                "새 리뷰",
                "정말 좋아요",
                testUserId,
                testBookId
        );

        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("새 리뷰"))
                .andExpect(jsonPath("$.content").value("정말 좋아요"));
    }

    @Test
    @DisplayName("내 리뷰 목록 조회 테스트")
    void getMyReviewsTest() throws Exception {
        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(reviewId));
    }

    @Test
    @DisplayName("리뷰 수정 테스트")
    void updateReviewTest() throws Exception {
        // UpdateReviewRequest 생성자 시그니처에 맞게 수정
        UpdateReviewRequest request = new UpdateReviewRequest(
                "수정된 리뷰",  // title
                "좋아요",       // content
                reviewId       // reviewId
        );

        mockMvc.perform(put("/reviews/" + reviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("좋아요"));
    }

    @Test
    @DisplayName("리뷰 삭제 테스트")
    void deleteReviewTest() throws Exception {
        mockMvc.perform(delete("/reviews/" + reviewId))
                .andExpect(status().isOk());
    }
}


