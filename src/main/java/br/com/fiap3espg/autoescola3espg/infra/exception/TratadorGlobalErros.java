package br.com.fiap3espg.autoescola3espg.infra.exception;

import br.com.fiap3espg.autoescola3espg.domain.aluno.AlunoNotFoundException;
import br.com.fiap3espg.autoescola3espg.domain.instrutor.InstrutorNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.fiap3espg.autoescola3espg.domain.instrucao.InstrucaoNotFoundException;
import br.com.fiap3espg.autoescola3espg.domain.instrucao.ValidacaoException;

import java.util.List;
import java.util.stream.Stream;

@RestControllerAdvice
public class TratadorGlobalErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Stream<DadosBadRequest>> tratarBadRequest(
            MethodArgumentNotValidException ex) {

        List<FieldError> erros = ex.getFieldErrors();

        return ResponseEntity
                .badRequest()
                .body(
                        erros
                                .stream()
                                .map(DadosBadRequest::new)
                );
    }

    @ExceptionHandler(InstrutorNotFoundException.class)
    public ResponseEntity<DadosMessage> tratarInstrutorNotFound(
            InstrutorNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new DadosMessage(ex.getMessage()));
    }

    @ExceptionHandler(AlunoNotFoundException.class)
    public ResponseEntity<DadosMessage> tratarAlunoNotFound(
            AlunoNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new DadosMessage(ex.getMessage()));
    }

    private record DadosBadRequest(
            String field,
            String message) {

        public DadosBadRequest(FieldError erro) {
            this(
                    erro.getField(),
                    erro.getDefaultMessage()
            );
        }
    }

    private record DadosMessage(
            String message) {
    }
    @ExceptionHandler(InstrucaoNotFoundException.class)
    public ResponseEntity<DadosMessage> tratarInstrucaoNotFound(
            InstrucaoNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new DadosMessage(ex.getMessage()));
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<DadosMessage> tratarValidacao(
            ValidacaoException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new DadosMessage(ex.getMessage()));
    }

    @ExceptionHandler(ConsultaCepException.class)
    public ResponseEntity<String> tratarConsultaCep(
            ConsultaCepException ex) {

        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.getMessage());
    }

}