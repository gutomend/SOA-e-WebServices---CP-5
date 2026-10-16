
package br.com.fiap3espg.autoescola3espg.controller;

import br.com.fiap3espg.autoescola3espg.domain.endereco.DadosViaCep;
import br.com.fiap3espg.autoescola3espg.service.ViaCepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class ViaCepController {

    private final ViaCepService viaCepService;

    @GetMapping("/cep/{cep}")
    public ResponseEntity<DadosViaCep> consultarCep(
            @PathVariable String cep) {

        DadosViaCep endereco = viaCepService.consultarCep(cep);

        return ResponseEntity.ok(endereco);
    }
}
