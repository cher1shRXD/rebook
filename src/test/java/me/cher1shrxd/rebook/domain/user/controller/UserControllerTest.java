package me.cher1shrxd.rebook.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.cher1shrxd.rebook.domain.user.dto.request.UpdateRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("내 정보 조회 테스트")
    void getMeTest() throws Exception {
        mockMvc.perform(get("/users/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nickname").exists())
                .andExpect(jsonPath("$.email").exists());
    }

    @Test
    @DisplayName("내 정보 수정 테스트")
    void updateMeTest() throws Exception {
        // UpdateRequest record는 모든 필드를 생성자에서 전달해야 함
        UpdateRequest updateRequest = new UpdateRequest(
                "test@test.com",  // email
                "새닉네임",        // nickname
                "password123",     // password
                "자기소개"         // bio
        );

        mockMvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value("새닉네임"))
                .andExpect(jsonPath("$.email").value("test@test.com"));
    }
}
