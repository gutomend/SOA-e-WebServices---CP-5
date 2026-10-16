package br.com.fiap3espg.autoescola3espg.domain.instrucao;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoInstrucao(

        @NotNull
        MotivoCancelamento motivo) {
}