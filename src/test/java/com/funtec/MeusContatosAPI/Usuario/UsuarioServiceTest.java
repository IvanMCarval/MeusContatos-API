package com.funtec.MeusContatosAPI.Usuario;

import com.funtec.MeusContatosAPI.Models.Endereco;
import com.funtec.MeusContatosAPI.Models.Usuario;
import com.funtec.MeusContatosAPI.Repository.UsuarioRepository;
import com.funtec.MeusContatosAPI.Services.Impl.UsuarioServiceImpl;
import com.funtec.MeusContatosAPI.Services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.when;
import org.assertj.core.api.Assertions;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service = new UsuarioServiceImpl();
    @Mock
    private UsuarioRepository repository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void deveSalvarUmUsuarioComEndereco() {

        Usuario usuario = criarUsuario();

        when(repository.save(usuario)).thenReturn(usuario);

        Usuario usuarioSalvo = service.salvarUsuario(usuario);

        String senhaCript = usuarioSalvo.getSenha();

        Assertions.assertThat(usuarioSalvo).isNotNull();
        Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1L);
        Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("Test");
        Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
        Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo(senhaCript);
        Assertions.assertThat(usuarioSalvo.getTelefone()).isEqualTo("16999999999");
        Assertions.assertThat(usuarioSalvo.getEndereco().getId()).isEqualTo(1L);
        Assertions.assertThat(usuarioSalvo.getEndereco().getCep()).isEqualTo("12345678");
        Assertions.assertThat(usuarioSalvo.getEndereco().getLogradouro()).isEqualTo("Rua");
        Assertions.assertThat(usuarioSalvo.getEndereco().getBairro()).isEqualTo("Bairro");
        Assertions.assertThat(usuarioSalvo.getEndereco().getLocalidade()).isEqualTo("Localidade");
        Assertions.assertThat(usuarioSalvo.getEndereco().getUf()).isEqualTo("SP");
        Assertions.assertThat(usuarioSalvo.getEndereco().getNumero()).isEqualTo("1111");
    }

    private static Usuario criarUsuario() {
        Endereco endereco = Endereco.builder()
                .id(1L)
                .cep("12345678")
                .logradouro("Rua")
                .bairro("Bairro")
                .localidade("Localidade")
                .uf("SP")
                .numero("1111")
                .build();

        return Usuario.builder()
                .id(1L)
                .nome("Test")
                .email("email@email.com")
                .senha("123")
                .telefone("16999999999")
                .endereco(endereco)
                .build();
    }
}
