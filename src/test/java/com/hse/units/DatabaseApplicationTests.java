package com.hse.units;

import com.hse.units.domain.Form;
import com.hse.units.repos.FormRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.hse.units.domain.Task;
import com.hse.units.domain.User;
import com.hse.units.repos.TaskRepository;
import com.hse.units.repos.UserRepository;

@SpringBootTest
class DatabaseApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private FormRepository formRepository;


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
        formRepository.removeMapping();
        taskRepository.deleteAll();

        if (!userRepository.existsUserByName("author")) {
            userRepository.save(new User("author", "b", "a@a"));
        }

        User author = userRepository.findUserByName("author");

        taskRepository.save(new Task("Zagadka", "some text", "otgadka", author.getUid()));
        taskRepository.save(new Task("Zagadka 2", "some text", "otgadka", author.getUid()));

        taskRepository.save(new Task("Mock", "jvfhebujrvbne", "1", author.getUid()));
        taskRepository.save(new Task("Mock", "hdvwrbfu3gbuv", "2", author.getUid()));

        Assertions.assertEquals(4, iteratorLength(taskRepository.findAll()));
        Assertions.assertTrue(taskRepository.findByTitle("Zagadka").get(0).checkCorrectness("otgadka"));
    }

    @Test
    void TestSimpleForms() {
        formRepository.removeMapping();
        taskRepository.deleteAll();
        formRepository.deleteAll();

        if (!userRepository.existsUserByName("author")) {
            userRepository.save(new User("author", "b", "a@a"));
        }

        User author = userRepository.findUserByName("author");

        taskRepository.save(new Task("Zagadka", "some text", "otgadka", author.getUid()));
        taskRepository.save(new Task("Zagadka 2", "some text", "otgadka", author.getUid()));

        taskRepository.save(new Task("Mock", "jvfhebujrvbne", "1", author.getUid()));
        taskRepository.save(new Task("Mock", "hdvwrbfu3gbuv", "2", author.getUid()));

        Form form = new Form("simple test", "Just answer the questions, bro", author.getUid());
        formRepository.save(form);

        Form result = formRepository.findFormByName("simple test");

        result.addTask(taskRepository.findByTitle("Zagadka").get(0));
        result.addTasks(taskRepository.findByTitle("Mock"));

        formRepository.save(result);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getTasks().size());

        result = formRepository.findFormByName("simple test");

        Assertions.assertTrue(result.getTasks().contains(taskRepository.findByTitle("Zagadka").get(0)));
    }

}
