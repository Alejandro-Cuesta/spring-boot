package dev.alejandro.spring.boot.controller;

import dev.alejandro.spring.boot.dto.TopicDTO;
import dev.alejandro.spring.boot.service.TopicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TopicControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TopicService topicService;

    @InjectMocks
    private TopicController topicController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(topicController).build();
    }

    @Test
    @DisplayName("GET /topics - Obtener todos los topics")
    void testGetAllTopics() throws Exception {
        TopicDTO t1 = new TopicDTO(1L, "Hardware");
        TopicDTO t2 = new TopicDTO(2L, "Software");

        when(topicService.getAllTopics()).thenReturn(Arrays.asList(t1, t2));

        mockMvc.perform(get("/topics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Hardware"))
                .andExpect(jsonPath("$[1].nombre").value("Software"));

        verify(topicService, times(1)).getAllTopics();
    }

    @Test
    @DisplayName("POST /topics - Crear topic")
    void testCreateTopic() throws Exception {
        TopicDTO saved = new TopicDTO(3L, "Red");

        when(topicService.createTopic(any(TopicDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Red\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nombre").value("Red"));

        verify(topicService, times(1)).createTopic(any(TopicDTO.class));
    }
}