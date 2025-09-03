package dev.alejandro.spring.boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//Manejo global de excepciones//
//Gracias a @ControllerAdvice, Spring captura las excepciones y las procesa aquí para devolver una respuesta HTTP uniforme//
@ControllerAdvice
public class GlobalExceptionHandler {

    //Manejo de excepciones cuando una solicitud (Request) no se encuentra//
    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<String> handleRequestNotFound(RequestNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    //Manejo genérico para cualquier otra excepción de Request//
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<String> handleRequestException(RequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    //Manejo de excepciones cuando un tema (Topic) no se encuentra//
    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<String> handleTopicNotFound(TopicNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    //Manejo genérico para cualquier otra excepción de Topic//
    @ExceptionHandler(TopicException.class)
    public ResponseEntity<String> handleTopicException(TopicException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    //Manejo de excepciones genéricas que no encajan en las anteriores//
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        //Aquí no devolvemos el mensaje original por seguridad, solo un texto genérico//
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor. Contacta al administrador.");
    }
}