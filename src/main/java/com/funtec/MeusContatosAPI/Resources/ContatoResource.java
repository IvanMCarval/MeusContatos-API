package com.funtec.MeusContatosAPI.Resources;

import com.funtec.MeusContatosAPI.Exceptions.RegraNegocioException;
import com.funtec.MeusContatosAPI.Models.Contato;
import com.funtec.MeusContatosAPI.Resources.DTO.ContatoDTO;
import com.funtec.MeusContatosAPI.Services.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                .build();

        try {
            Contato contatoCriado = service.criarContato(contato);
            return new ResponseEntity<>(contatoCriado, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
