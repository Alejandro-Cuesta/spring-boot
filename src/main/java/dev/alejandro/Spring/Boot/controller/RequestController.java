package dev.alejandro.spring.boot.controller;

import dev.alejandro.spring.boot.dto.RequestDTO;
import dev.alejandro.spring.boot.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    // Listar todas las solicitudes
    @GetMapping
    public List<RequestDTO> getAllRequests() {
        return requestService.getAllRequests();
    }

    // Listar solicitudes pendientes
    @GetMapping("/pending")
    public List<RequestDTO> getPendingRequests() {
        return requestService.getPendingRequests();
    }

    // Crear solicitud
    @PostMapping
    public ResponseEntity<RequestDTO> createRequest(@RequestBody RequestDTO dto) {
        RequestDTO created = requestService.createRequest(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Marcar como atendida
    @PutMapping("/{id}/attend")
    public ResponseEntity<RequestDTO> markAsAttended(@PathVariable Long id, @RequestParam String nombreAtendio) {
        RequestDTO updated = requestService.markAsAttended(id, nombreAtendio);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Editar solicitud
    @PutMapping("/{id}")
    public ResponseEntity<RequestDTO> updateRequest(@PathVariable Long id, @RequestBody RequestDTO dto) {
        RequestDTO updated = requestService.updateRequest(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Eliminar solicitud
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        boolean deleted = requestService.deleteRequest(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}