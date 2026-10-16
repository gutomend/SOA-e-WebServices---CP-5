package br.com.fiap3espg.autoescola3espg.service;

import br.com.fiap3espg.autoescola3espg.domain.aluno.Aluno;
import br.com.fiap3espg.autoescola3espg.domain.aluno.AlunoNotFoundException;
import br.com.fiap3espg.autoescola3espg.domain.aluno.AlunoRepository;
import br.com.fiap3espg.autoescola3espg.domain.instrucao.*;
import br.com.fiap3espg.autoescola3espg.domain.instrucao.validacao.ValidadorAgendamento;
import br.com.fiap3espg.autoescola3espg.domain.instrucao.validacao.ValidadorAlunoAtivo;
import br.com.fiap3espg.autoescola3espg.domain.instrucao.validacao.ValidadorInstrutorAtivo;
import br.com.fiap3espg.autoescola3espg.domain.instrutor.Instrutor;
import br.com.fiap3espg.autoescola3espg.domain.instrutor.InstrutorNotFoundException;
import br.com.fiap3espg.autoescola3espg.domain.instrutor.InstrutorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstrucaoService {
    private final InstrucaoRepository repository;
    private final AlunoRepository alunoRepository;
    private final InstrutorRepository instrutorRepository;
    private final List<ValidadorAgendamento> validadoresAgendamento;

    public DadosDetalhamentoAgendamento agendarInstrucao(DadosAgendamentoInstrucao dados) {
        if (!alunoRepository.existsById(dados.idAluno())) {
            throw new AlunoNotFoundException("ID do aluno informado não existe!");
        }
        if (dados.idInstrutor() != null && !instrutorRepository.existsById(dados.idInstrutor())) {
            throw new InstrutorNotFoundException("ID do instrutor informado não existe!");
        }
        //Validações
         validadoresAgendamento.forEach(validador -> validador.validar(dados));

        Aluno aluno = alunoRepository.getReferenceById(dados.idAluno());
        Instrutor instrutor = escolherInstrutor(dados);
        if (instrutor == null) {
            throw new ValidacaoException("Nenhum instrutor disponível para a data/hora informada!");
        }

        Instrucao instrucao = new Instrucao(null, aluno, instrutor, dados.dataHora());
        Instrucao salvo = repository.save(instrucao);
        return new DadosDetalhamentoAgendamento(salvo);
    }

    private Instrutor escolherInstrutor(DadosAgendamentoInstrucao dados) {

        if (dados.idInstrutor() != null) {
            return instrutorRepository.getReferenceById(dados.idInstrutor());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException(
                    "Especialidade é obrigatória se o instrutor não for informado!"
            );
        }

        return instrutorRepository.escolherInstrutorAleatorioDisponivel(
                dados.especialidade(),
                dados.dataHora().minusHours(1),
                dados.dataHora().plusHours(1)
        );
    }
    @Transactional
    public void cancelarInstrucao(
            Long id,
            DadosCancelamentoInstrucao dados) {

        Instrucao instrucao = repository.findById(id)
                .orElseThrow(() ->
                        new InstrucaoNotFoundException(
                                "ID da instrução informado não existe!"
                        )
                );

        if (instrucao.isCancelada()) {
            throw new ValidacaoException(
                    "Esta instrução já foi cancelada!"
            );
        }

        LocalDateTime limiteCancelamento =
                LocalDateTime.now().plusHours(24);

        if (limiteCancelamento.isAfter(instrucao.getDataHora())) {
            throw new ValidacaoException(
                    "O cancelamento deve ser realizado com no mínimo 24 horas de antecedência!"
            );
        }

        instrucao.cancelar(dados.motivo());
    }
}