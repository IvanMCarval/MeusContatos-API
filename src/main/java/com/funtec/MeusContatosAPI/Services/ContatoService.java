package com.funtec.MeusContatosAPI.Services;

import com.funtec.MeusContatosAPI.Models.Contato;

import java.util.List;
import java.util.Optional;
public interface ContatoService {
    List<Contato> findAll();
    List<Contato> findByUsuarioId(Long id);
    Optional<Contato> findById(Long id);
    Contato criarContato(Contato contato);
    Contato atualizarContato (Long id, Contato contato);
    void deletarContato (Long id);
}
