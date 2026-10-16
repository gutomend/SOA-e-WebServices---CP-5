
package br.com.fiap3espg.autoescola3espg.infra.exception;

import org.springframework.http.HttpStatus;

public class ConsultaCepException extends RuntimeException {

    private final HttpStatus status;

    public ConsultaCepException(String mensagem, HttpStatus status) {
        super(mensagem);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
