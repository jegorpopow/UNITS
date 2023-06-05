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
        if (request.getCookies() == null) {
            return;
        }
        final String jwt = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equalsIgnoreCase("jwtAccessToken"))
                .findFirst().map(Cookie::getValue).orElse(null);
        if (jwt == null) {
            return; // TODO: throw smth?
        }
        System.out.println("logout, and jwt is:" + jwt);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            System.out.println("set token in db to expired");
            storedToken.setExpired(1);
            storedToken.setRevoked(1);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
