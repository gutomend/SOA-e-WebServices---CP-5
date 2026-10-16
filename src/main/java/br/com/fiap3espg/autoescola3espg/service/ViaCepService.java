
package br.com.fiap3espg.autoescola3espg.service;

import br.com.fiap3espg.autoescola3espg.domain.endereco.DadosViaCep;
import br.com.fiap3espg.autoescola3espg.infra.exception.ConsultaCepException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class ViaCepService {

    private final RestClient restClient;

    public ViaCepService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://viacep.com.br/ws")
                .build();

    }

    public DadosViaCep consultarCep(String cep) {

        String cepLimpo = cep.replaceAll("\\D", "");

        if (!cepLimpo.matches("\\d{8}")) {
            throw new ConsultaCepException(
                    "CEP invalido. Informe exatamente 8 digitos.",
                    HttpStatus.BAD_REQUEST
            );
        }

        try {
            DadosViaCep endereco = restClient.get()
                    .uri("/{cep}/json/", cepLimpo)
                    .retrieve()
                    .body(DadosViaCep.class);

            if (endereco == null) {
                throw new ConsultaCepException(
                        "Resposta vazia do ViaCEP.",
                        HttpStatus.BAD_GATEWAY
                );
            }

            if (Boolean.TRUE.equals(endereco.erro())) {
                throw new ConsultaCepException(
                        "CEP nao encontrado.",
                        HttpStatus.NOT_FOUND
                );
            }

            return endereco;

        } catch (ConsultaCepException ex) {
            throw ex;

        } catch (RestClientException ex) {
            throw new ConsultaCepException(
                    "Falha ao consultar o servico ViaCEP.",
                    HttpStatus.BAD_GATEWAY
            );
        }
    }
}
