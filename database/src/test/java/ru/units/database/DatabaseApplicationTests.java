package ru.units.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DatabaseApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;


    private int iteratorLength(Iterable<User> iterable) {
        int size = 0;

        for (User user : iterable) {
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

        userRepository.save(new User("Jack", "Bauer"));
        userRepository.save(new User("Chloe", "O'Brian"));
        userRepository.save(new User("Kim", "Bauer"));
        userRepository.save(new User("David", "Palmer"));
        userRepository.save(new User("Michelle", "Dessler"));

        Assertions.assertEquals(5, iteratorLength(userRepository.findAll()));
        Assertions.assertEquals("[User UID=1, name=`Jack`, info=`Bauer`]", userRepository.findByUid(1).toString());
    }

    @Test
    void TestTaskDatabase() {
        taskRepository.deleteAll();

        taskRepository.save(new Task());
        userRepository.save(new User("Chloe", "O'Brian"));
        userRepository.save(new User("Kim", "Bauer"));
        userRepository.save(new User("David", "Palmer"));
        userRepository.save(new User("Michelle", "Dessler"));

        Assertions.assertEquals(5, iteratorLength(userRepository.findAll()));
        Assertions.assertEquals("[User UID=1, name=`Jack`, info=`Bauer`]", userRepository.findByUid(1).toString());
    }


}
