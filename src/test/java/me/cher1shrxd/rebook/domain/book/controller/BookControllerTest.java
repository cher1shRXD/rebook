package me.cher1shrxd.rebook.domain.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.cher1shrxd.rebook.domain.book.dto.BookResponse;
import me.cher1shrxd.rebook.domain.book.dto.CreateBookReq;
import me.cher1shrxd.rebook.domain.book.dto.UpdateBookReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("내 책 목록 조회")
    void getMyBookTest() throws Exception {
        mockMvc.perform(get("/books/my"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("책 단건 조회")
    void getBookTest() throws Exception {
        // 실제 DB에 id=1L 책이 존재해야 함
        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("책 생성")
    void createBookTest() throws Exception {
        CreateBookReq request = new CreateBookReq("title", "author");

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("책 수정")
    void updateBookTest() throws Exception {
        UpdateBookReq request = new UpdateBookReq("new title", "new author");

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("책 삭제")
    void deleteBookTest() throws Exception {
        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());
    }
}

