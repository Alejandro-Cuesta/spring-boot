package dev.alejandro.spring.boot.controller;

import dev.alejandro.spring.boot.dto.RequestDTO;
import dev.alejandro.spring.boot.service.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RequestService requestService;

    @InjectMocks
    private RequestController requestController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(requestController).build();
    }

    @Test
    @DisplayName("GET /requests - Obtener solicitudes pendientes")
    void testGetPendingRequests() throws Exception {
        RequestDTO dto = new RequestDTO();
        dto.setNombreSolicitante("Ana");
        dto.setDescripcion("PC no funciona");
        dto.setTemaId(1L);
        dto.setFechaSolicitud(LocalDateTime.now());

        when(requestService.getPendingRequests()).thenReturn(Arrays.asList(dto));

        mockMvc.perform(get("/requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreSolicitante").value("Ana"));

        verify(requestService, times(1)).getPendingRequests();
    }

    @Test
    @DisplayName("POST /requests - Crear solicitud")
    void testCreateRequest() throws Exception {
        RequestDTO dto = new RequestDTO();
        dto.setNombreSolicitante("Luis");
        dto.setDescripcion("Problema con la impresora");
        dto.setTemaId(2L);

        when(requestService.createRequest(any(RequestDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombreSolicitante\":\"Luis\",\"descripcion\":\"Problema con la impresora\",\"temaId\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreSolicitante").value("Luis"));

        verify(requestService, times(1)).createRequest(any(RequestDTO.class));
    }
}