package com.funtec.MeusContatosAPI.Resources;

import com.funtec.MeusContatosAPI.Exceptions.RegraNegocioException;
import com.funtec.MeusContatosAPI.Models.Contato;
import com.funtec.MeusContatosAPI.Models.Endereco;
import com.funtec.MeusContatosAPI.Resources.DTO.AtualizarContatoDTO;
import com.funtec.MeusContatosAPI.Resources.DTO.ContatoDTO;
import com.funtec.MeusContatosAPI.Resources.DTO.EnderecoDTO;
import com.funtec.MeusContatosAPI.Services.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("contato")
public class ContatoResource {
    @Autowired
    private ContatoService service;

    @PostMapping("/criarContato")
    public ResponseEntity<?> criarContato(@RequestBody ContatoDTO contatoDTO){
        Contato contato = Contato.builder()
                .nome(contatoDTO.getNome())
                .email(contatoDTO.getEmail())
                .telefone(contatoDTO.getTelefone())
                .endereco(contatoDTO.getEndereco())
                .usuarioId(contatoDTO.getUsuarioId())
                .build();

        try {
            Contato contatoCriado = service.criarContato(contato);
            return new ResponseEntity<>(contatoCriado, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Contato> listaDeContato = service.findAll();
        List<AtualizarContatoDTO> contatoDTOList = converterContatoParaDTO(listaDeContato);
        return ResponseEntity.ok(contatoDTOList);
    }

    @GetMapping("/{id}/contatos")
    public ResponseEntity<?> findByUsuarioId(@PathVariable("id") Long id){
        List<Contato> contatoList = service.findByUsuarioId(id);
        List<AtualizarContatoDTO> contatoDTOList = converterContatoParaDTO(contatoList);
        return ResponseEntity.ok(contatoDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        Optional<Contato> contato = service.findById(id);

        if (!contato.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AtualizarContatoDTO contatoDTO = converterContatoParaDTO(contato);

        return ResponseEntity.ok(contatoDTO);
    }

    @PutMapping("/{id}/atualizar-contato")
    public ResponseEntity<?> atualizarContato(@PathVariable("id") Long id, @RequestBody AtualizarContatoDTO atualizarContatoDTO){
        try {
            Contato contato = converterDTOParaContato(atualizarContatoDTO);

            Contato contatoAtualizado = service.atualizarContato(id, contato);

            AtualizarContatoDTO contatoDTO = converterContatoParaDTO(contatoAtualizado);
            return ResponseEntity.ok(contatoDTO);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body("Erro ao tentar atualizar o contato: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}/deletar-contato")
    public ResponseEntity<?> deletarContato(@PathVariable("id") Long id){
        try {
            service.deletarContato(id);
            return ResponseEntity.ok("Deletado com sucesso");
        } catch (RegraNegocioException e){
            return new ResponseEntity<>("Erro ao tentar deletar", HttpStatus.BAD_REQUEST);
        }
    }


    private static Contato converterDTOParaContato(AtualizarContatoDTO atualizarContatoDTO) {
        Contato contato = new Contato();
        contato.setUsuarioId(atualizarContatoDTO.getUsuarioId());
        contato.setId(atualizarContatoDTO.getId());
        contato.setNome(atualizarContatoDTO.getNome());
        contato.setEmail(atualizarContatoDTO.getEmail());
        contato.setTelefone(atualizarContatoDTO.getTelefone());

        Endereco endereco = new Endereco();
        endereco.setId(atualizarContatoDTO.getEndereco().getId());
        endereco.setCep(atualizarContatoDTO.getEndereco().getCep());
        endereco.setLogradouro(atualizarContatoDTO.getEndereco().getLogradouro());
        endereco.setBairro(atualizarContatoDTO.getEndereco().getBairro());
        endereco.setNumero(atualizarContatoDTO.getEndereco().getNumero());
        endereco.setLocalidade(atualizarContatoDTO.getEndereco().getLocalidade());
        endereco.setUf(atualizarContatoDTO.getEndereco().getUf());

        contato.setEndereco(endereco);

        return contato;
    }

    private static AtualizarContatoDTO converterContatoParaDTO(Contato contato) {
        AtualizarContatoDTO contatoDTO = new AtualizarContatoDTO();
        contatoDTO.setUsuarioId(contatoDTO.getUsuarioId());
        contatoDTO.setId(contato.getId());
        contatoDTO.setNome(contato.getNome());
        contatoDTO.setEmail(contato.getEmail());
        contatoDTO.setTelefone(contato.getTelefone());

        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setId(contato.getEndereco().getId());
        enderecoDTO.setCep(contato.getEndereco().getCep());
        enderecoDTO.setLogradouro(contato.getEndereco().getLogradouro());
        enderecoDTO.setBairro(contato.getEndereco().getBairro());
        enderecoDTO.setNumero(contato.getEndereco().getNumero());
        enderecoDTO.setLocalidade(contato.getEndereco().getLocalidade());
        enderecoDTO.setUf(contato.getEndereco().getUf());

        contatoDTO.setEndereco(enderecoDTO);

        return contatoDTO;
    }

    private static AtualizarContatoDTO converterContatoParaDTO(Optional<Contato> contato) {
        AtualizarContatoDTO contatoDTO = new AtualizarContatoDTO();
        contatoDTO.setUsuarioId(contato.get().getUsuarioId());
        contatoDTO.setId(contato.get().getId());
        contatoDTO.setNome(contato.get().getNome());
        contatoDTO.setEmail(contato.get().getEmail());
        contatoDTO.setTelefone(contato.get().getTelefone());

        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setId(contato.get().getEndereco().getId());
        enderecoDTO.setCep(contato.get().getEndereco().getCep());
        enderecoDTO.setLogradouro(contato.get().getEndereco().getLogradouro());
        enderecoDTO.setBairro(contato.get().getEndereco().getBairro());
        enderecoDTO.setNumero(contato.get().getEndereco().getNumero());
        enderecoDTO.setLocalidade(contato.get().getEndereco().getLocalidade());
        enderecoDTO.setUf(contato.get().getEndereco().getUf());

        contatoDTO.setEndereco(enderecoDTO);

        return contatoDTO;
    }

    private static List<AtualizarContatoDTO> converterContatoParaDTO(List<Contato> contatos){
        List<AtualizarContatoDTO> contatoDTOList = new ArrayList<>();

        for (Contato contato : contatos) {
            AtualizarContatoDTO contatoDTO = new AtualizarContatoDTO();
            contatoDTO.setUsuarioId(contato.getUsuarioId());
            contatoDTO.setId(contato.getId());
            contatoDTO.setNome(contato.getNome());
            contatoDTO.setEmail(contato.getEmail());
            contatoDTO.setTelefone(contato.getTelefone());

            EnderecoDTO enderecoDTO = new EnderecoDTO();
            enderecoDTO.setId(contato.getEndereco().getId());
            enderecoDTO.setCep(contato.getEndereco().getCep());
            enderecoDTO.setLogradouro(contato.getEndereco().getLogradouro());
            enderecoDTO.setBairro(contato.getEndereco().getBairro());
            enderecoDTO.setNumero(contato.getEndereco().getNumero());
            enderecoDTO.setLocalidade(contato.getEndereco().getLocalidade());
            enderecoDTO.setUf(contato.getEndereco().getUf());

            contatoDTO.setEndereco(enderecoDTO);

            contatoDTOList.add(contatoDTO);
        }

        return contatoDTOList;
    }
}
