package br.com.fiap3espg.autoescola3espg.domain.instrucao.validacao;

import br.com.fiap3espg.autoescola3espg.domain.instrucao.DadosAgendamentoInstrucao;
import br.com.fiap3espg.autoescola3espg.domain.instrucao.InstrucaoRepository;
import br.com.fiap3espg.autoescola3espg.domain.instrucao.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ValidadorLimiteDiarioAluno implements ValidadorAgendamento {

    private final InstrucaoRepository repository;

    @Override
    public void validar(DadosAgendamentoInstrucao dados) {

        LocalDate data = dados.dataHora().toLocalDate();

        LocalDateTime inicioDia = data.atStartOfDay();
        LocalDateTime fimDia = data.plusDays(1).atStartOfDay();

        long quantidade =
                repository
                        .countByAlunoIdAndCanceladaFalseAndDataHoraGreaterThanEqualAndDataHoraLessThan(
                                dados.idAluno(),
                                inicioDia,
                                fimDia
                        );

        if (quantidade >= 2) {
            throw new ValidacaoException(
                    "O aluno já possui o limite de duas instruções agendadas neste dia!"
            );
        }
    }
}