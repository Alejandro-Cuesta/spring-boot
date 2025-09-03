package dev.alejandro.spring.boot.exception;

//Excepción base para cualquier error relacionado con temas (Topic)//
public class TopicException extends RuntimeException {

    public TopicException(String message) {
        super(message);
    }

    public TopicException(String message, Throwable cause) {
        super(message, cause);
    }
}