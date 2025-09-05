package dev.alejandro.spring.boot.exception.topic;

//Excepción que representa el caso en el que un tema (Topic) no es encontrado//
//Será capturada por GlobalExceptionHandler para devolver un 404 (Not Found)//
public class TopicNotFoundException extends TopicException {

    public TopicNotFoundException(String message) {
        super(message);
    }

    public TopicNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}