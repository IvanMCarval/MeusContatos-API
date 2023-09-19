package com.funtec.MeusContatosAPI.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "endereco")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cep")
    private String cep;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "localidade")
    private String localidade;

    @Column(name = "uf")
    private String uf;

    @Column(name = "numero")
    private String numero;

    @OneToOne(mappedBy = "endereco")
    private Usuario usuario;

    @OneToOne(mappedBy = "endereco")
    private Contato contato;
}
