package com.funtec.MeusContatosAPI.Resources.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAuthDTO {

    private String email;
    private String senha;
}
