
package br.com.fiap3espg.autoescola3espg.service;

import br.com.fiap3espg.autoescola3espg.domain.instrutor.*;
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
class InstrutorServiceTest {

    @Mock
    private InstrutorRepository repository;

    @InjectMocks
    private InstrutorService service;

    private Instrutor instrutor;

    @BeforeEach
    void preparar() {
        instrutor = mock(Instrutor.class);
    }

    @Test
    void deveExcluirInstrutorLogicamente() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(instrutor));

        when(repository.save(instrutor))
                .thenReturn(instrutor);

        service.excluirInstrutor(1L);

        verify(instrutor).excluir();
        verify(repository).save(instrutor);
    }

    @Test
    void deveLancarExcecaoQuandoInstrutorNaoExiste() {

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                InstrutorNotFoundException.class,
                () -> service.excluirInstrutor(99L)
        );

        verify(repository, never()).save(any());
    }
}
