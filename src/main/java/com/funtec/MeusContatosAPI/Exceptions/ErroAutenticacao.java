package com.funtec.MeusContatosAPI.Exceptions;

public class ErroAutenticacao extends RuntimeException{

    public ErroAutenticacao(String message) {
        super(message);
    }
}
