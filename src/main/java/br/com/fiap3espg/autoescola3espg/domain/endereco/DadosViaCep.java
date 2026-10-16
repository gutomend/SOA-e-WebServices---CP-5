
package br.com.fiap3espg.autoescola3espg.domain.endereco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosViaCep(
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String uf,
        String estado,
        String regiao,
        String ibge,
        String ddd,
        Boolean erro
) {
}
