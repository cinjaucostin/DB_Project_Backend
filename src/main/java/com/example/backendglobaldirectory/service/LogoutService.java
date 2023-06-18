package com.example.backendglobaldirectory.service;

import com.example.backendglobaldirectory.entities.Token;
import com.example.backendglobaldirectory.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class LogoutService implements LogoutHandler {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String authHeader = request.getHeader("Authorization");
        String jwtToken;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        jwtToken = authHeader.substring("Bearer ".length());

        Token tokenFromDb = this.tokenRepository.findByToken(jwtToken)
                .orElse(null);

        if(tokenFromDb == null) {
            return;
        }

        tokenFromDb.setExpired(true);
        tokenFromDb.setRevoked(true);

        tokenRepository.save(tokenFromDb);

    }

}
