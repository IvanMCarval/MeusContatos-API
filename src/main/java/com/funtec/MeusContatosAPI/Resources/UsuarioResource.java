package com.funtec.MeusContatosAPI.Resources;

import com.funtec.MeusContatosAPI.Exceptions.ErroAutenticacao;
import com.funtec.MeusContatosAPI.Exceptions.RegraNegocioException;
import com.funtec.MeusContatosAPI.Models.Usuario;
import com.funtec.MeusContatosAPI.Resources.DTO.AtualizarUsuarioDTO;
import com.funtec.MeusContatosAPI.Resources.DTO.TokenDTO;
import com.funtec.MeusContatosAPI.Resources.DTO.UsuarioAuthDTO;
import com.funtec.MeusContatosAPI.Resources.DTO.UsuarioDTO;
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

        AtualizarUsuarioDTO usuarioDTO = converterParaDTO(usuario);

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
            Usuario usuario = converterDTO(atualizarUsuarioDTO);

            usuario = service.atualizarUsuario(id, usuario);
            return ResponseEntity.ok(usuario);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Erro ao tentar atualizar usuario: " + e.getMessage());
        }

    }

    private Usuario converterDTO(AtualizarUsuarioDTO atualizarUsuarioDTO) {
        Usuario usuario = new Usuario();

        usuario.setId(atualizarUsuarioDTO.getId());
        usuario.setNome(atualizarUsuarioDTO.getNome());
        usuario.setEmail(atualizarUsuarioDTO.getEmail());
        usuario.setTelefone(atualizarUsuarioDTO.getTelefone());
        usuario.setEndereco(atualizarUsuarioDTO.getEndereco());

        return usuario;
    }

    private AtualizarUsuarioDTO converterParaDTO(Optional<Usuario> usuario) {
        AtualizarUsuarioDTO atualizarUsuarioDTO = new AtualizarUsuarioDTO();

        atualizarUsuarioDTO.setId(usuario.get().getId());
        atualizarUsuarioDTO.setNome(usuario.get().getNome());
        atualizarUsuarioDTO.setEmail(usuario.get().getEmail());
        atualizarUsuarioDTO.setTelefone(usuario.get().getTelefone());
        atualizarUsuarioDTO.setEndereco(usuario.get().getEndereco());

        return atualizarUsuarioDTO;
    }
}
