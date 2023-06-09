package com.hse.units;

import com.hse.units.domain.Form;
import com.hse.units.domain.TaskTag;
import com.hse.units.repos.FormRepository;
import com.hse.units.repos.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.hse.units.domain.Task;
import com.hse.units.domain.User;
import com.hse.units.repos.TaskRepository;
import com.hse.units.repos.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

@SpringBootTest
class DatabaseApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private TagRepository tagRepository;


    private void prepareTasks() {
        formRepository.removeMapping();
        taskRepository.deleteAll();

        if (!userRepository.existsUserByName("author")) {
            userRepository.save(new User("author", "b", "a@a"));
        }

        if (!tagRepository.existsTagByName("good task")) {
            tagRepository.save(new TaskTag("good task", "just good task"));
        }

        User author = userRepository.findUserByName("author").orElseThrow(() -> new UsernameNotFoundException("User not found"));
        TaskTag tag = tagRepository.findByName("good task").get(0);

        Task first_task = new Task("Zagadka", "some text", "otgadka", author.getUid(), true, true);
        first_task.addTag(tag);

        taskRepository.save(first_task);
        taskRepository.save(new Task("Zagadka 2", "some text", "otgadka", author.getUid(), true, true));

        taskRepository.save(new Task("Mock", "jvfhebujrvbne", "1", author.getUid(), true, true));
        taskRepository.save(new Task("Mock", "hdvwrbfu3gbuv", "2", author.getUid(), false, true));
    }

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
        prepareTasks();
        TaskTag tag = tagRepository.findByName("good task").get(0);

        Assertions.assertEquals(4, iteratorLength(taskRepository.findAll()));
        Assertions.assertTrue(taskRepository.findByTitle("Zagadka").get(0).checkCorrectness("otgadka"));
        Assertions.assertEquals(Set.of(tag), taskRepository.findByTitle("Zagadka").get(0).getTags());

        Assertions.assertEquals(1,
                taskRepository.findTaskByTagsId(tag.getId()).size());

        Assertions.assertEquals(taskRepository.findTaskByTagsId(tag.getId()).get(0),
                taskRepository.findByTitle("Zagadka").get(0));

    }

    @Test
    void TestSimpleForms() {
        prepareTasks();
        formRepository.deleteAll();

        User author = userRepository.findUserByName("author").orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Form form = new Form("simple test",
                "Just answer the questions, bro",
                author.getUid(),
                false);
        formRepository.save(form);

        Form result = formRepository.findFormByName("simple test");

        result.addTask(taskRepository.findByTitle("Zagadka").get(0));
        result.addTasks(taskRepository.findByTitle("Mock"));

        formRepository.save(result);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getTasks().size());

        result = formRepository.findFormByName("simple test");

        Assertions.assertTrue(result.getTasks().contains(taskRepository.findByTitle("Zagadka").get(0)));
        Assertions.assertEquals(result.getTasks().get(0), taskRepository.findByTitle("Zagadka").get(0));
        Assertions.assertEquals(result.getTasks().get(1).getTitle(), "Mock");
    }

}
