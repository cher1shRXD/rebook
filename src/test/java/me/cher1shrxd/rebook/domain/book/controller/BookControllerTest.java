package me.cher1shrxd.rebook.domain.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.cher1shrxd.rebook.domain.auth.dto.request.LoginRequest;
import me.cher1shrxd.rebook.domain.book.dto.CreateBookReq;
import me.cher1shrxd.rebook.domain.book.dto.UpdateBookReq;
import me.cher1shrxd.rebook.domain.book.entity.BookEntity;
import me.cher1shrxd.rebook.domain.book.repository.BookRepository;
import me.cher1shrxd.rebook.global.security.jwt.dto.JwtResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private BookRepository bookRepository;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    @DisplayName("내 책 목록 조회")
    @Sql("/insert-data.sql")
    void getMyBookTest() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user1@rebook.com", "qwer1234!!");
        String loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn()
                .getResponse()
                .getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(loginResult, JwtResponse.class);
        
        mockMvc.perform(get("/books/my")
                        .header("Authorization", "Bearer " + jwtResponse.accessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("책 단건 조회")
    @Sql("/insert-data.sql")
    void getBookTest() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user1@rebook.com", "qwer1234!!");
        String loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn()
                .getResponse()
                .getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(loginResult, JwtResponse.class);

        BookEntity book = bookRepository.findAllByAddedBy_Email("user1@rebook.com").stream()
                .filter(b -> b.getTitle().equals("스프링 부트와 AWS로 혼자 구현하는 웹 서비스"))
                .findFirst()
                .orElseThrow();
        
        mockMvc.perform(get("/books/" + book.getId())
                        .header("Authorization", "Bearer " + jwtResponse.accessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value("스프링 부트와 AWS로 혼자 구현하는 웹 서비스"))
                .andExpect(jsonPath("$.author").value("이동욱"));
    }

    @Test
    @DisplayName("책 생성")
    @Sql("/insert-data.sql")
    void createBookTest() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user1@rebook.com", "qwer1234!!");
        String loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn()
                .getResponse()
                .getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(loginResult, JwtResponse.class);
        
        CreateBookReq request = new CreateBookReq("새로운 책", "새로운 저자");

        mockMvc.perform(post("/books")
                        .header("Authorization", "Bearer " + jwtResponse.accessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("새로운 책"))
                .andExpect(jsonPath("$.author").value("새로운 저자"));
    }

    @Test
    @DisplayName("책 수정")
    @Sql("/insert-data.sql")
    void updateBookTest() throws Exception {
        LoginRequest loginRequest = new LoginRequest("admin@rebook.com", "qwer1234!!");
        String loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn()
                .getResponse()
                .getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(loginResult, JwtResponse.class);
        
        UpdateBookReq request = new UpdateBookReq("클린 코드 (개정판)", "로버트 C. 마틴");

        BookEntity book = bookRepository.findAll().stream()
                .filter(b -> b.getTitle().equals("클린 코드"))
                .findFirst()
                .orElseThrow();
        
        mockMvc.perform(put("/books/" + book.getId())
                        .header("Authorization", "Bearer " + jwtResponse.accessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("클린 코드 (개정판)"));
    }

    @Test
    @DisplayName("책 삭제")
    @Sql("/insert-data.sql")
    void deleteBookTest() throws Exception {
        LoginRequest loginRequest = new LoginRequest("admin@rebook.com", "qwer1234!!");
        String loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn()
                .getResponse()
                .getContentAsString();
        JwtResponse jwtResponse = objectMapper.readValue(loginResult, JwtResponse.class);
        
        // Get the actual book ID for the book added by admin
        BookEntity book = bookRepository.findAll().stream()
                .filter(b -> b.getTitle().equals("클린 코드"))
                .findFirst()
                .orElseThrow();
        
        mockMvc.perform(delete("/books/" + book.getId())
                        .header("Authorization", "Bearer " + jwtResponse.accessToken()))
                .andExpect(status().isNoContent());
    }
}

