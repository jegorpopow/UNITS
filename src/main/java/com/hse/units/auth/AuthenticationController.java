package com.hse.units.auth;

import com.hse.units.config.LogoutService;
import com.hse.units.domain.User;
import com.hse.units.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String user(Principal principal) {
        if (principal.getName() == null) {
            return "redirect:/login";
        }
        Long userId = userService.findUserByUsername(principal.getName());
        return "redirect:/user/" + userId;
    }

    @GetMapping("/user/{id}")
    public String userProfile(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


    /*@PostMapping("/registration")
    public String registrationSubmit(@RequestParam String username, @RequestParam String password, Model model) {
        User user = new User(username, password, null); //add encryption
        userService.createUser(user);
        return "redirect:/login"; //autologin and move to user/{id}
    }*/ // Old version
    @PostMapping("/registration")
    public ModelAndView register(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        var get = authenticationService.register(new RegisterRequest(username, password));
        Cookie cookie = new Cookie("jwtAccessToken", get.getAccessToken());
        cookie.setAttribute("jwtRefreshToken", get.getRefreshToken());
        cookie.setMaxAge(60 * 60); // 1 hour
        response.addCookie(cookie);

        return new ModelAndView("login");
        //return ResponseEntity.ok(service.register(request));
    }

    @GetMapping("/login")
    public String login() {
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
            return new ModelAndView("/login").addObject("message","incorrect username or password");
        }
        return new ModelAndView("redirect:/user");
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        return new ModelAndView("redirect:/login");
    }
}
