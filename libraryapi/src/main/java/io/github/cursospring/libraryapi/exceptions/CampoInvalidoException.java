package io.github.cursospring.libraryapi.exceptions;

public class CampoInvalidoException extends RuntimeException {

    private String campo;

    public CampoInvalidoException(String campo, String mensagem) {
        super(mensagem);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}
