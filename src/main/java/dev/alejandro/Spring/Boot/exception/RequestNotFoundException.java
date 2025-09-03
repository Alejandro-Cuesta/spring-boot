package dev.alejandro.spring.boot.exception;

//Excepción que representa el caso en el que una solicitud (Request) no es encontrada//
//Será capturada por GlobalExceptionHandler para devolver un 404 (Not Found)//
public class RequestNotFoundException extends RequestException {

    public RequestNotFoundException(String message) {
        super(message);
    }

    public RequestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}