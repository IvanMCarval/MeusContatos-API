package com.funtec.MeusContatosAPI.Services.Impl;

import com.funtec.MeusContatosAPI.Exceptions.ErroAutenticacao;
import com.funtec.MeusContatosAPI.Exceptions.RegraNegocioException;
import com.funtec.MeusContatosAPI.Models.Endereco;
import com.funtec.MeusContatosAPI.Models.Usuario;
import com.funtec.MeusContatosAPI.Repository.UsuarioRepository;
import com.funtec.MeusContatosAPI.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        verificarEmailExistente(usuario.getEmail());
        criptografarSenha(usuario);
        return repository.save(usuario);
    }

    private void criptografarSenha(Usuario usuario) {
        String senha = usuario.getSenha();
        String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
        usuario.setSenha(senhaCriptografada);
    }

    @Override
    public void verificarEmailExistente(String email) {
        boolean emailExiste = repository.existsByEmail(email);

        if (emailExiste) {
            throw new RegraNegocioException("Este email ja está cadastrado em nosso sistema");
        }
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Usuario usuario = repository.findByEmail(email);

        boolean senhaAutenticada = passwordEncoder.matches(senha, usuario.getSenha());

        if (!senhaAutenticada) {
            throw new ErroAutenticacao("Erro na autenticação");
        }

        return usuario;
    }

    @Override
    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuario) {
        try {
            Usuario entity = repository.getReferenceById(id);
            atualizaUsuario(entity, usuario);
            return repository.save(entity);
        } catch (RegraNegocioException e) {
            throw new RegraNegocioException("Usuario não encontrado: " + e.getMessage());
        }

    }

    private void atualizaUsuario(Usuario entity, Usuario obj) {
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
}
