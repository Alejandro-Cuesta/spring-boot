package dev.alejandro.spring.boot.controller;

import dev.alejandro.spring.boot.dto.RequestDTO;
import dev.alejandro.spring.boot.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Listar todas las solicitudes")
    public List<RequestDTO> getAllRequests() {
        return requestService.getAllRequests();
    }
    
    // Listar solicitudes pendientes
    @GetMapping("/pending")
    @Operation(summary = "Listar solicitudes pendientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de solicitudes pendientes"),
            @ApiResponse(responseCode = "404", description = "No se encontraron solicitudes")
    })
    public List<RequestDTO> getPendingRequests() {
        return requestService.getPendingRequests();
    }

    // Crear solicitud
    @PostMapping
    @Operation(summary = "Crear una nueva solicitud")
    public ResponseEntity<RequestDTO> createRequest(@RequestBody RequestDTO dto) {
        RequestDTO created = requestService.createRequest(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    // Marcar como atendida
    @PutMapping("/{id}/attend")
    @Operation(summary = "Marcar solicitud como atendida")
    public ResponseEntity<RequestDTO> markAsAttended(@PathVariable Long id, @RequestParam String nombreAtendio) {
        RequestDTO updated = requestService.markAsAttended(id, nombreAtendio);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Editar solicitud
    @PutMapping("/{id}")
    @Operation(summary = "Editar solicitud existente")
    public ResponseEntity<RequestDTO> updateRequest(@PathVariable Long id, @RequestBody RequestDTO dto) {
        RequestDTO updated = requestService.updateRequest(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Eliminar solicitud
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar solicitud")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        boolean deleted = requestService.deleteRequest(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Manejo de errores específicos 
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleNotFound(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleInvalidState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}