package br.com.fiap3espg.autoescola3espg.controller;

import br.com.fiap3espg.autoescola3espg.domain.aluno.Aluno;
import br.com.fiap3espg.autoescola3espg.domain.aluno.AlunoRepository;
import br.com.fiap3espg.autoescola3espg.domain.instrucao.DadosAgendamentoInstrucao;
import br.com.fiap3espg.autoescola3espg.domain.instrucao.DadosDetalhamentoAgendamento;
import br.com.fiap3espg.autoescola3espg.domain.instrucao.Instrucao;
import br.com.fiap3espg.autoescola3espg.domain.instrucao.InstrucaoRepository;
import br.com.fiap3espg.autoescola3espg.domain.instrutor.Instrutor;
import br.com.fiap3espg.autoescola3espg.domain.instrutor.InstrutorRepository;
import br.com.fiap3espg.autoescola3espg.service.InstrucaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import br.com.fiap3espg.autoescola3espg.domain.instrucao.DadosCancelamentoInstrucao;

import br.com.fiap3espg.autoescola3espg.domain.instrucao.DadosCancelamentoInstrucao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/instrucoes")
@RequiredArgsConstructor
public class InstrucaoController {
    private final InstrucaoService service;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoAgendamento> agendarInstrucao(@RequestBody @Valid DadosAgendamentoInstrucao dados) {
        return ResponseEntity.ok(service.agendarInstrucao(dados));
    }
    @PutMapping("/{id}/cancelamento")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> cancelarInstrucao(
            @PathVariable Long id,
            @RequestBody @Valid DadosCancelamentoInstrucao dados) {

        service.cancelarInstrucao(id, dados);
        return ResponseEntity.noContent().build();
    }
    }