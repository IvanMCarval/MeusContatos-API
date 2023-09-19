package com.funtec.MeusContatosAPI.Resources.DTO;

import com.funtec.MeusContatosAPI.Models.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarContatoDTO {
    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private EnderecoDTO endereco;

    private Long id_usuario;
}
