package com.funtec.MeusContatosAPI.Resources;

import com.funtec.MeusContatosAPI.Exceptions.ErroAutenticacao;
import com.funtec.MeusContatosAPI.Exceptions.RegraNegocioException;
import com.funtec.MeusContatosAPI.Models.Endereco;
import com.funtec.MeusContatosAPI.Models.Usuario;
import com.funtec.MeusContatosAPI.Resources.DTO.*;
import com.funtec.MeusContatosAPI.Services.Impl.TokenService;
import com.funtec.MeusContatosAPI.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioService service;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<?> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .telefone(usuarioDTO.getTelefone())
                .endereco(usuarioDTO.getEndereco())
                .build();

        try {
            Usuario usuarioSalvo = service.salvarUsuario(usuario);
            return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<?> autenticarUsuario(@RequestBody UsuarioAuthDTO usuarioAuthDTO) {
        try{
            Usuario usarioAutenticado = service.autenticar(usuarioAuthDTO.getEmail(), usuarioAuthDTO.getSenha());

            var token = tokenService.gerarToken(usarioAutenticado);
            TokenDTO tokenDTO = new TokenDTO(token);

            return ResponseEntity.ok(tokenDTO);
        } catch (ErroAutenticacao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {

        Optional<Usuario> usuario = service.findById(id);

        if (!usuario.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AtualizarUsuarioDTO usuarioDTO = converteUsuarioParaDTO(usuario);

        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Usuario> listaDeUsuarios = service.findAll();
        return ResponseEntity.ok(listaDeUsuarios);
    }

    @PutMapping("/{id}/atualizar-usuario")
    public ResponseEntity<?> atualizarUsuario(@PathVariable("id") Long id, @RequestBody AtualizarUsuarioDTO atualizarUsuarioDTO) {
        try {
            Usuario usuario = converterDeDTOParaUsuario(atualizarUsuarioDTO);

            Usuario usuarioAtualizado = service.atualizarUsuario(id, usuario);

            AtualizarUsuarioDTO usuarioDTO = converteUsuarioParaDTO(usuarioAtualizado);

            return ResponseEntity.ok(usuarioDTO);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Erro ao tentar atualizar usuario: " + e.getMessage());
        }

    }

    private Usuario converterDeDTOParaUsuario(AtualizarUsuarioDTO atualizarUsuarioDTO) {
        Usuario usuario = new Usuario();

        usuario.setId(atualizarUsuarioDTO.getId());
        usuario.setNome(atualizarUsuarioDTO.getNome());
        usuario.setEmail(atualizarUsuarioDTO.getEmail());
        usuario.setTelefone(atualizarUsuarioDTO.getTelefone());

        Endereco endereco = new Endereco();
        endereco.setId(atualizarUsuarioDTO.getEndereco().getId());
        endereco.setCep(atualizarUsuarioDTO.getEndereco().getCep());
        endereco.setLogradouro(atualizarUsuarioDTO.getEndereco().getLogradouro());
        endereco.setBairro(atualizarUsuarioDTO.getEndereco().getBairro());
        endereco.setNumero(atualizarUsuarioDTO.getEndereco().getNumero());
        endereco.setLocalidade(atualizarUsuarioDTO.getEndereco().getLocalidade());
        endereco.setUf(atualizarUsuarioDTO.getEndereco().getUf());

        usuario.setEndereco(endereco);

        return usuario;
    }

    private AtualizarUsuarioDTO converteUsuarioParaDTO(Usuario usuario) {
        AtualizarUsuarioDTO atualizarUsuarioDTO = new AtualizarUsuarioDTO();

        atualizarUsuarioDTO.setId(usuario.getId());
        atualizarUsuarioDTO.setNome(usuario.getNome());
        atualizarUsuarioDTO.setEmail(usuario.getEmail());
        atualizarUsuarioDTO.setTelefone(usuario.getTelefone());

        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setId(usuario.getEndereco().getId());
        enderecoDTO.setCep(usuario.getEndereco().getCep());
        enderecoDTO.setLogradouro(usuario.getEndereco().getLogradouro());
        enderecoDTO.setBairro(usuario.getEndereco().getBairro());
        enderecoDTO.setNumero(usuario.getEndereco().getNumero());
        enderecoDTO.setLocalidade(usuario.getEndereco().getLocalidade());
        enderecoDTO.setUf(usuario.getEndereco().getUf());

        atualizarUsuarioDTO.setEndereco(enderecoDTO);

        return atualizarUsuarioDTO;
    }

    private static AtualizarUsuarioDTO converteUsuarioParaDTO(Optional<Usuario> usuario) {
        AtualizarUsuarioDTO usuarioDTO = new AtualizarUsuarioDTO();
        usuarioDTO.setId(usuario.get().getId());
        usuarioDTO.setNome(usuario.get().getNome());
        usuarioDTO.setEmail(usuario.get().getEmail());
        usuarioDTO.setTelefone(usuario.get().getTelefone());

        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setId(usuario.get().getEndereco().getId());
        enderecoDTO.setCep(usuario.get().getEndereco().getCep());
        enderecoDTO.setLogradouro(usuario.get().getEndereco().getLogradouro());
        enderecoDTO.setBairro(usuario.get().getEndereco().getBairro());
        enderecoDTO.setNumero(usuario.get().getEndereco().getNumero());
        enderecoDTO.setLocalidade(usuario.get().getEndereco().getLocalidade());
        enderecoDTO.setUf(usuario.get().getEndereco().getUf());

        usuarioDTO.setEndereco(enderecoDTO);

        return usuarioDTO;
    }
}
