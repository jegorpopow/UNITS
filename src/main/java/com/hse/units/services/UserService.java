package com.hse.units.services;

import com.hse.units.domain.User;
import com.hse.units.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.UUID;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    public boolean createUser(User user) {
        if (userRepository.existsUserByName(user.getName())) {
            return false;
        }

        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Добро пожаловать, %s! \n" +
                    "Пожалуйста, подтвердите регистрацию: http://localhost:8080/activate/%s",
                    user.getName(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);


        }

        return true;
    }

    public User getUserById(Long id) {

        return new User("user", "password", null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public Long findUserByUsername(String name) {
        return 1L;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        userRepository.save(user);

        return true;
    }
}
