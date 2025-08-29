package dev.alejandro.spring.boot.controller;

import dev.alejandro.spring.boot.dto.TopicDTO;
import dev.alejandro.spring.boot.service.TopicService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TopicController.class)
class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopicService topicService;

    @Test
    @DisplayName("GET /api/topics - Obtener todos los topics")
    void testGetAllTopics() throws Exception {
        TopicDTO t1 = new TopicDTO(1L, "Hardware");
        TopicDTO t2 = new TopicDTO(2L, "Software");

        when(topicService.getAllTopics()).thenReturn(Arrays.asList(t1, t2));

        mockMvc.perform(get("/api/topics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Hardware"))
                .andExpect(jsonPath("$[1].nombre").value("Software"));

        verify(topicService, times(1)).getAllTopics();
    }

    @Test
    @DisplayName("POST /api/topics - Crear topic")
    void testCreateTopic() throws Exception {
        TopicDTO saved = new TopicDTO(3L, "Red");

        when(topicService.createTopic(any(TopicDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Red\"}"))
                .andExpect(status().isCreated()) // 201 CREATED
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nombre").value("Red"));

        verify(topicService, times(1)).createTopic(any(TopicDTO.class));
    }
}