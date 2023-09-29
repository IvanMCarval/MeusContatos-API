package com.funtec.MeusContatosAPI.Services;

import com.funtec.MeusContatosAPI.Models.Usuario;
import com.funtec.MeusContatosAPI.Resources.DTO.AtualizarUsuarioDTO;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    Usuario salvarUsuario(Usuario usuario);
    Usuario autenticar(String email, String senha);
    Usuario atualizarUsuario(Long id, Usuario usuario);
    void verificarEmailExistente(String email);
    void verificarUsuarioEmailExistente(String email, Long id);
}
