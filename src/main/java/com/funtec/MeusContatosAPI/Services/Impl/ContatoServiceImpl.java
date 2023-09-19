package com.funtec.MeusContatosAPI.Services.Impl;

import com.funtec.MeusContatosAPI.Exceptions.RegraNegocioException;
import com.funtec.MeusContatosAPI.Models.Contato;
import com.funtec.MeusContatosAPI.Models.Endereco;
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
        return repository.findAll();
    }

    @Override
    public Optional<Contato> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Contato criarContato(Contato contato) {
        return repository.save(contato);
    }

    @Override
    @Transactional
    public Contato atualizarContato(Long id, Contato contato) {
        try{
            Contato entity = repository.getReferenceById(id);
            atualizarContato(entity, contato);
            return repository.save(entity);
        } catch (RegraNegocioException e){
            throw new RegraNegocioException("Contato não encontrado: " + e.getMessage());
        }
    }

    private void atualizarContato(Contato entity, Contato obj){
        entity.setNome(obj.getNome());
        entity.setEmail(obj.getEmail());
        entity.setTelefone(obj.getTelefone());

        Endereco endereco = entity.getEndereco();
        endereco.setCep(obj.getEndereco().getCep());
        endereco.setLogradouro(obj.getEndereco().getLogradouro());
        endereco.setBairro(obj.getEndereco().getBairro());
        endereco.setNumero(obj.getEndereco().getNumero());
        endereco.setLocalidade(obj.getEndereco().getLocalidade());
        endereco.setUf(obj.getEndereco().getUf());
    }

    @Override
    public void deletarContato(Long id) {
        repository.deleteById(id);
    }
}


