package io.github.cursospring.libraryapi.exceptions;

public class RegistroDuplicado extends RuntimeException{
    public RegistroDuplicado(String message) {
        super(message);
    }
}
