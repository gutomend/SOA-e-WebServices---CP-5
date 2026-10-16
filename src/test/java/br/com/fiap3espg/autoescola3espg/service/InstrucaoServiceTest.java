
package br.com.fiap3espg.autoescola3espg.service;

import br.com.fiap3espg.autoescola3espg.domain.instrucao.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstrucaoServiceTest {

    @Mock
    private InstrucaoRepository repository;

    @InjectMocks
    private InstrucaoService service;

    @Test
    void deveCancelarInstrucaoComAntecedencia() {

        Instrucao instrucao = new Instrucao(
                1L,
                null,
                null,
                LocalDateTime.now().plusDays(3)
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(instrucao));

        DadosCancelamentoInstrucao dados =
                new DadosCancelamentoInstrucao(
                        MotivoCancelamento.ALUNO_DESISTIU
                );

        service.cancelarInstrucao(1L, dados);

        assertTrue(instrucao.isCancelada());

        assertEquals(
                MotivoCancelamento.ALUNO_DESISTIU,
                instrucao.getMotivoCancelamento()
        );
    }

    @Test
    void naoDeveCancelarInstrucaoComMenosDe24Horas() {

        Instrucao instrucao = new Instrucao(
                1L,
                null,
                null,
                LocalDateTime.now().plusHours(2)
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(instrucao));

        DadosCancelamentoInstrucao dados =
                new DadosCancelamentoInstrucao(
                        MotivoCancelamento.ALUNO_DESISTIU
                );

        assertThrows(
                ValidacaoException.class,
                () -> service.cancelarInstrucao(1L, dados)
        );

        assertFalse(instrucao.isCancelada());
    }
}
