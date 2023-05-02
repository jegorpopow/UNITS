package ru.units.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.units.domain.Task;
import ru.units.domain.User;
import ru.units.repos.TaskRepository;
import ru.units.repos.UserRepository;

@SpringBootTest
class DatabaseApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;


    private int iteratorLength(Iterable<?> iterable) {
        int size = 0;

        for (Object item : iterable) {
            size++;
        }

        return size;
    }


    /*
     * NEVER RUN IT ON PRODUCTION SERVER!
     */
    @Test
    void TestUserDatabase() {
        userRepository.deleteAll();

        userRepository.save(new User("Jack", "Bauer", null));
        userRepository.save(new User("Chloe", "O'Brian", null));
        userRepository.save(new User("Kim", "Bauer", null));
        userRepository.save(new User("David", "Palmer", null));
        userRepository.save(new User("Michelle", "Dessler", null));

        Assertions.assertEquals(5, iteratorLength(userRepository.findAll()));
        Assertions.assertEquals("[[name=`Jack`, info=`Bauer`]]",
                userRepository.findByName("Jack").toString());
    }

    @Test
    void TestTaskDatabase() {
        taskRepository.deleteAll();

        if (!userRepository.existsUserByName("author")) {
            userRepository.save(new User("author", "b", "a@a"));
        }

        User author = userRepository.findUserByName("author");

        taskRepository.save(new Task("Zagadka", "some text", "otgadka", author.getUid()));
        taskRepository.save(new Task("Zagadka 2", "some text", "otgadka", author.getUid()));

        Assertions.assertEquals(2, iteratorLength(taskRepository.findAll()));
        Assertions.assertTrue(taskRepository.findByAuthor(author.getUid()).get(0).checkCorrectness("otgadka"));


    }


}
