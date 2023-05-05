package com.hse.units;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hse.units.repos.TaskRepository;
import com.hse.units.repos.UserRepository;

@SpringBootApplication
@RestController
@EnableJpaRepositories("com.hse.units.repos")
public class DatabaseApplication {
    @Autowired
    TaskRepository tasks;

    @Autowired
    UserRepository users;

    public static void main(String[] args) {
        SpringApplication.run(DatabaseApplication.class, args);
    }

    @GetMapping("/users")
    public String users(@RequestParam("uid") long uid) {
        return users.findByUid(uid).toString();
    }

}
