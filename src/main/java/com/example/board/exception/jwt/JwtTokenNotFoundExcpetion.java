package com.example.board.exception.jwt;

import io.jsonwebtoken.JwtException;

public class JwtTokenNotFoundExcpetion extends JwtException{

    public JwtTokenNotFoundExcpetion() {
        super("JWT not Found");

    }

  
}
