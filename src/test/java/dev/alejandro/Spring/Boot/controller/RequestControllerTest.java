package dev.alejandro.spring.boot.controller;

import dev.alejandro.spring.boot.dto.RequestDTO;
import dev.alejandro.spring.boot.service.RequestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RequestController.class)
class RequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestService requestService;

    @Test
    @DisplayName("GET /api/requests/pending - Obtener solicitudes pendientes")
    void testGetPendingRequests() throws Exception {
        RequestDTO dto = new RequestDTO();
        dto.setNombreSolicitante("Ana");
        dto.setDescripcion("PC no funciona");
        dto.setTemaId(1L);
        dto.setFechaSolicitud(LocalDateTime.now());

        when(requestService.getPendingRequests()).thenReturn(Arrays.asList(dto));

        mockMvc.perform(get("/api/requests/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreSolicitante").value("Ana"));

        verify(requestService, times(1)).getPendingRequests();
    }

    @Test
    @DisplayName("POST /api/requests - Crear solicitud")
    void testCreateRequest() throws Exception {
        RequestDTO dto = new RequestDTO();
        dto.setId(1L);
        dto.setNombreSolicitante("Luis");
        dto.setDescripcion("Problema con la impresora");
        dto.setTemaId(2L);

        when(requestService.createRequest(any(RequestDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombreSolicitante\":\"Luis\",\"descripcion\":\"Problema con la impresora\",\"temaId\":2}"))
                .andExpect(status().isCreated()) // 201 CREATED
                .andExpect(jsonPath("$.nombreSolicitante").value("Luis"));

        verify(requestService, times(1)).createRequest(any(RequestDTO.class));
    }
}