package com.hse.units.auth;

import com.hse.units.config.JwtService;
import com.hse.units.config.LogoutService;
import com.hse.units.domain.User;
import com.hse.units.repos.TokenRepository;
import com.hse.units.services.MailSender;
import com.hse.units.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Arrays;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Autowired
    private UserService userService;

    private boolean isAlreadyAuthorized(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return false;
        }
        final String jwt = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equalsIgnoreCase("jwtAccessToken"))
                .findFirst().map(Cookie::getValue).orElse(null);
        if (jwt == null) {
            return false;
        }
        final String username = jwtService.extractUsername(jwt);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        var isTokenValid = tokenRepository.findByToken(jwt)
                .map(t -> (t.getExpired() == 0) && (t.getRevoked() == 0))
                .orElse(false);
        return username != null && jwtService.isTokenValid(jwt, userDetails) && isTokenValid;
    }

    @GetMapping("/registration")
    public String registration(HttpServletRequest request) {
        if (isAlreadyAuthorized(request)) {
            return "redirect:/user";
        }
        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView register(@RequestParam String username, @RequestParam String password,
                                 @RequestParam String firstName, @RequestParam String lastName,
                                 @RequestParam String email, HttpServletResponse response, Model model) {
        var get = authenticationService.register(new RegisterRequest(username, password, firstName, lastName, email));
        if (get == null) {
            return new ModelAndView("/registration").addObject("param", "error"); // todo
        }
        Cookie cookie = new Cookie("jwtAccessToken", get.getAccessToken());
        cookie.setAttribute("jwtRefreshToken", get.getRefreshToken());
        cookie.setMaxAge(60 * 60); // 1 hour
        response.addCookie(cookie);

        return new ModelAndView("redirect:/user");
        //return ResponseEntity.ok(service.register(request));
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        if (isAlreadyAuthorized(request)) {
            return "redirect:/user";
        }
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        try {
            var get = authenticationService.authenticate(new AuthenticationRequest(username, password));
            Cookie cookie = new Cookie("jwtAccessToken", get.getAccessToken());
            cookie.setAttribute("jwtRefreshToken", get.getRefreshToken()); // todo: it probably doesn't work. For now it's enough to get only access token
            cookie.setMaxAge(60 * 60); // 1 hour
            cookie.setSecure(true);
            response.addCookie(cookie);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            System.out.println(e.getMessage());
            return new ModelAndView("/login").addObject("message", "incorrect username or password");
        }
        return new ModelAndView("redirect:/user");
    }

    @GetMapping("/logoutdone")
    public String logout(/*HttpServletRequest request*/) {
        System.out.println("In logout mapping");
        return "redirect:/";
    }
}
