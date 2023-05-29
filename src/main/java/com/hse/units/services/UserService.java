package com.hse.units.services;

import com.hse.units.domain.User;
import com.hse.units.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service    
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public void createUser(User user) {
        if (isUserExists(user)) {
            return;
        }
        userRepository.save(user);
    }

    public boolean isUserExists(User user) {
        return userRepository.existsUserByName(user.getName());
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
}
