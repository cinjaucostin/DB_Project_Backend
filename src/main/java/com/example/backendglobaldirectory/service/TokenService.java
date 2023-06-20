package com.example.backendglobaldirectory.service;

import com.example.backendglobaldirectory.entities.Token;
import com.example.backendglobaldirectory.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public void revokeAllTokensForUser(int uid) {
        List<Token> tokensToRevoke = this.tokenRepository.findAllValidTokensByUser(uid);

        tokensToRevoke.forEach((token -> {
            token.setExpired(true);
            token.setRevoked(true);
        }));

        tokenRepository.saveAll(tokensToRevoke);
    }

}
