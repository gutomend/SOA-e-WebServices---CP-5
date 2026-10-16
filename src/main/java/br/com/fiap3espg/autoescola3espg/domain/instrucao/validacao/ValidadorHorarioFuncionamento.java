package br.com.fiap3espg.autoescola3espg.domain.instrucao.validacao;

import br.com.fiap3espg.autoescola3espg.domain.instrucao.DadosAgendamentoInstrucao;
import br.com.fiap3espg.autoescola3espg.domain.instrucao.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamento {

    private static final LocalTime ABERTURA = LocalTime.of(6, 0);
    private static final LocalTime ULTIMO_HORARIO = LocalTime.of(20, 0);

    @Override
    public void validar(DadosAgendamentoInstrucao dados) {

        DayOfWeek diaSemana = dados.dataHora().getDayOfWeek();
        LocalTime horario = dados.dataHora().toLocalTime();

        boolean domingo = diaSemana == DayOfWeek.SUNDAY;

        boolean antesDaAbertura =
                horario.isBefore(ABERTURA);

        boolean depoisDoUltimoHorario =
                horario.isAfter(ULTIMO_HORARIO);

        if (domingo || antesDaAbertura || depoisDoUltimoHorario) {
            throw new ValidacaoException(
                    "Agendamento permitido somente de segunda a sábado, " +
                            "com início entre 06:00 e 20:00."
            );
        }
    }
}