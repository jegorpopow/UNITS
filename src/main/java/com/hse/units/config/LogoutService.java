package com.hse.units.config;

import com.hse.units.repos.TokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //final String authHeader = request.getHeader("Authorization");
        //final String jwt;

        final String jwt = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equalsIgnoreCase("jwtAccessToken"))
                .findFirst().map(Cookie::getValue).orElse(null);

        if (jwt == null) {
            return; // TODO: throw smth?
        }

        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        System.out.println("logout, and jwt is:" + jwt);
        if (storedToken != null) {
            storedToken.setExpired(1);
            storedToken.setRevoked(1);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
