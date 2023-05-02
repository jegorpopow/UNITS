package ru.units;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.units.repos.TaskRepository;
import ru.units.repos.UserRepository;

@SpringBootApplication
@RestController
@EnableJpaRepositories("ru.units.repos")
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
