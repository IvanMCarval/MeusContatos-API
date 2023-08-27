package com.funtec.MeusContatosAPI.Services;

import com.funtec.MeusContatosAPI.Models.Usuario;

public interface UsuarioService {

    Usuario salvarUsuario(Usuario usuario);
    Usuario autenticar(String email, String senha);
    void verificarEmailExistente(String email);
}
