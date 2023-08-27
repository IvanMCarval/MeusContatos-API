package com.funtec.MeusContatosAPI.Resources;

import com.funtec.MeusContatosAPI.Exceptions.ErroAutenticacao;
import com.funtec.MeusContatosAPI.Exceptions.RegraNegocioException;
import com.funtec.MeusContatosAPI.Models.Usuario;
import com.funtec.MeusContatosAPI.Resources.DTO.TokenDTO;
import com.funtec.MeusContatosAPI.Resources.DTO.UsuarioAuthDTO;
import com.funtec.MeusContatosAPI.Resources.DTO.UsuarioDTO;
import com.funtec.MeusContatosAPI.Services.Impl.TokenService;
import com.funtec.MeusContatosAPI.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
