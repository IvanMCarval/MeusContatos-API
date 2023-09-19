package com.funtec.MeusContatosAPI.Services.Impl;

import com.funtec.MeusContatosAPI.Models.Contato;
import com.funtec.MeusContatosAPI.Repository.ContatoRepository;
import com.funtec.MeusContatosAPI.Services.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class ContatoServiceImpl implements ContatoService {
    @Autowired
    private ContatoRepository repository;
    @Override
    public List<Contato> findAll() {
        return null;
    }

    @Override
    public Optional<Contato> findById(Long id) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public Contato criarContato(Contato contato) {
        return repository.save(contato);
    }

    @Override
    public Contato atualizarContato(Long id, Contato contato) {
        return null;
    }

    @Override
    public void deletarContato(Long id) {

    }
}


