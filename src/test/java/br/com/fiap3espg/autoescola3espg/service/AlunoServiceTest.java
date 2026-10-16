
package br.com.fiap3espg.autoescola3espg.service;

import br.com.fiap3espg.autoescola3espg.domain.aluno.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @Mock
    private AlunoRepository repository;

    @InjectMocks
    private AlunoService service;

    private Aluno aluno;

    @BeforeEach
    void preparar() {
        aluno = mock(Aluno.class);
    }

    @Test
    void deveExcluirAlunoLogicamente() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(aluno));

        service.excluirAluno(1L);

        verify(aluno).excluir();
        verify(repository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoAlunoNaoExiste() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                AlunoNotFoundException.class,
                () -> service.excluirAluno(99L)
        );

        verify(repository, never()).save(any());
    }
}
