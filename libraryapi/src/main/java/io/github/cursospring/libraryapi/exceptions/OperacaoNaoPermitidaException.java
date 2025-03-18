package io.github.cursospring.libraryapi.exceptions;

public class OperacaoNaoPermitidaException extends RuntimeException{
    public OperacaoNaoPermitidaException(String message) {
        super(message);
    }
}
