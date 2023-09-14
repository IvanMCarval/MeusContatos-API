package com.funtec.MeusContatosAPI.Resources.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {
    private Long id;

    private String cep;

    private String logradouro;

    private String bairro;

    private String localidade;

    private String uf;

    private String numero;

}
