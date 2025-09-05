package dev.alejandro.spring.boot.exception.request;


//Excepción base para cualquier error relacionado con solicitudes (Request)//
//Se extiende de RuntimeException para que Spring pueda capturarla sin necesidad de declararla explícitamente//

public class RequestException extends RuntimeException {

    public RequestException(String message) {
        super(message);
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }
}