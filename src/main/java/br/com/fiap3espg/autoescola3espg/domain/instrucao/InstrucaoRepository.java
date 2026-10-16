package br.com.fiap3espg.autoescola3espg.domain.instrucao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface InstrucaoRepository extends JpaRepository<Instrucao, Long> {

    boolean existsByInstrutorIdAndCanceladaFalseAndDataHoraAfterAndDataHoraBefore(
            Long idInstrutor,
            LocalDateTime inicioConflito,
            LocalDateTime fimConflito
    );

    long countByAlunoIdAndCanceladaFalseAndDataHoraGreaterThanEqualAndDataHoraLessThan(
            Long idAluno,
            LocalDateTime inicioDia,
            LocalDateTime fimDia
    );
}