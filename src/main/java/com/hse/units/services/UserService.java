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

    public boolean createUser(User user) {
        if (userRepository.existsUserByName(user.getName())) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public User getUserById(Long id) {
        return userRepository.findByUid(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public Long findUserByUsername(String name) {
        return userRepository.findUserByName(name).getUid();
    }
}
