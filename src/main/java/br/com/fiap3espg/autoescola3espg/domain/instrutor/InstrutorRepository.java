package br.com.fiap3espg.autoescola3espg.domain.instrutor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {

    Page<Instrutor> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
    select i from Instrutor i
    where
        i.ativo = true
        and i.especialidade = :especialidade
        and i.id not in (
            select instrucao.instrutor.id
            from Instrucao instrucao
            where
                instrucao.cancelada = false
                and instrucao.dataHora > :inicioConflito
                and instrucao.dataHora < :fimConflito
        )
    order by rand()
    limit 1
""")
    Instrutor escolherInstrutorAleatorioDisponivel(
            Especialidade especialidade,
            LocalDateTime inicioConflito,
            LocalDateTime fimConflito
    );
    boolean existsByIdAndAtivoFalse(Long id);
}