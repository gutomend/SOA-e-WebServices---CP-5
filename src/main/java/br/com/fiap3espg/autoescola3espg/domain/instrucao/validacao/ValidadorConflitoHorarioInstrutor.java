package br.com.fiap3espg.autoescola3espg.domain.instrucao.validacao;

import br.com.fiap3espg.autoescola3espg.domain.instrucao.DadosAgendamentoInstrucao;
import br.com.fiap3espg.autoescola3espg.domain.instrucao.InstrucaoRepository;
import br.com.fiap3espg.autoescola3espg.domain.instrucao.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ValidadorConflitoHorarioInstrutor implements ValidadorAgendamento {

    private final InstrucaoRepository repository;

    @Override
    public void validar(DadosAgendamentoInstrucao dados) {

        // O instrutor pode não ser informado.
        // Nesse caso ele será escolhido automaticamente depois.
        if (dados.idInstrutor() == null) {
            return;
        }

        LocalDateTime inicioConflito = dados.dataHora().minusHours(1);
        LocalDateTime fimConflito = dados.dataHora().plusHours(1);

        boolean instrutorOcupado =
                repository
                        .existsByInstrutorIdAndCanceladaFalseAndDataHoraAfterAndDataHoraBefore(
                                dados.idInstrutor(),
                                inicioConflito,
                                fimConflito
                        );

        if (instrutorOcupado) {
            throw new ValidacaoException(
                    "Instrutor ocupado no período solicitado!"
            );
        }
    }
}