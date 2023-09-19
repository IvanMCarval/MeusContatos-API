package com.funtec.MeusContatosAPI.Repository;

import com.funtec.MeusContatosAPI.Models.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {
}
