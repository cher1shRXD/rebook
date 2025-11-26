package me.cher1shrxd.rebook.domain.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.cher1shrxd.rebook.domain.auth.dto.request.LoginRequest;
import me.cher1shrxd.rebook.domain.auth.dto.request.ReissueRequest;
import me.cher1shrxd.rebook.domain.auth.dto.request.SignupRequest;
import me.cher1shrxd.rebook.global.security.jwt.dto.JwtResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 API 통합 테스트")
    void signupTest() throws Exception {
        SignupRequest request = new SignupRequest("username", "test@test.com", "password", "nickname");

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 API 통합 테스트")
    void loginTest() throws Exception {
        LoginRequest request = new LoginRequest("test@test.com", "password");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    @DisplayName("토큰 재발급 API 통합 테스트")
    void reissueTest() throws Exception {
        ReissueRequest request = new ReissueRequest("refreshToken");

        mockMvc.perform(post("/auth/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }
}
