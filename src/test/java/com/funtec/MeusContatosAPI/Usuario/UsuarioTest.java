package com.funtec.MeusContatosAPI.Usuario;

import com.funtec.MeusContatosAPI.Models.Endereco;
import com.funtec.MeusContatosAPI.Models.Usuario;
import com.funtec.MeusContatosAPI.Repository.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioTest {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void deveCadastrarUsuarioComEndereco() {
        Usuario usuario = criarUsuario();

        Usuario usuarioSalvo = repository.save(usuario);

        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }

    @Test
    public void deveVerificarExistenciaDeUmEmailNaBaseDeDados() {
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        boolean result = repository.existsByEmail("email@email.com");

        Assertions.assertThat(result).isTrue();
    }

    private static Usuario criarUsuario() {
        Endereco endereco = Endereco.builder()
                .cep("12345678")
                .logradouro("Rua")
                .bairro("Bairro")
                .localidade("Localidade")
                .uf("SP")
                .numero("1111")
                .build();

        return Usuario.builder()
                .nome("Test")
                .email("email@email.com")
                .senha("123")
                .telefone("16999999999")
                .endereco(endereco)
                .build();
    }
}
