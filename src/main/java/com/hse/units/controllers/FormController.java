package com.hse.units.controllers;

import com.hse.units.domain.*;
import com.hse.units.repos.*;
import com.hse.units.services.FormService;
import com.hse.units.services.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.json.*;

import java.util.*;

@Controller
public class FormController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    ResponseRepository responseRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TagRepository tagRepository;

    private final FormService formService;

    private final UserService userService;

    private final int PAGE_SIZE = 10;

    private FormGenerator formGenerator;

    public FormController(FormService formService, UserService userService) {
        this.formService = formService;
        this.userService = userService;
    }

    @PostConstruct
    public void initializeFormGenerator() {
        this.formGenerator = new FormGenerator(taskRepository, formService);
    }

    private void ifAuthorized(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("ifAuthorized: " + login);
        model.addAttribute("user", login);
    }

    @GetMapping("/forms")
    public String tasks(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        ifAuthorized(model);
        model.addAttribute("forms", formService.getForms().stream()
                .filter(form -> form.getPrivacy().equals("OPEN")));
        return findPaginated(1, model);
    }

    @GetMapping("/forms/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        Page<Form> page = formService.findPaginated(pageNo, PAGE_SIZE);
        List<Form> tasks = page.getContent();
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageTotal", page.getTotalPages());
        model.addAttribute("tasks", tasks);
        return "forms";
    }

    /*
        @RequestMapping("/form/{id}")
        public String formInfo(@PathVariable Long id, Model model) {
            ifAuthorized(model);
            Form form = formService.getFormById(id);

            model.addAttribute("form", form);

            return "form";
        }
    */


    @RequestMapping("/form_response/{id}")
    public String responseInfo(@PathVariable Long id, Model model) {
        ifAuthorized(model);
        String currentUsername = (String) model.getAttribute("user");

        FormResponse response = formService.getResponse(id);
        model.addAttribute("response", response);

        Form form = response.getForm();
        if (!userRepository.findByUid(form.getCreatorId()).getUsername().equals(currentUsername) &&
                !response.getUser().getUsername().equals(currentUsername)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "you don't have access to this page"
            );
        }


        Map<Task, String> answers = new HashMap<>();
        Map<Task, Boolean> correctness = new HashMap<>();

        for (Answer answer : response.getAnswers()) {
            answers.put(answer.getTask(), answer.getAnswer());
            correctness.put(answer.getTask(), answer.getResult());
        }

        model.addAttribute("answers", answers);
        model.addAttribute("correctness", correctness);

        return "response";
    }

    @RequestMapping("answer_form/{id}")
    public String formAnswer(@PathVariable Long id, HttpServletRequest request, Model model) {
        ifAuthorized(model);

        User user = null;

        if (model.containsAttribute("user")) {
            user = userRepository.findUserByName((String) model.getAttribute("user")).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        } else {
            user = userRepository.findUserByName("author").orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }

        Form form = formService.getFormById(id);
        FormResponse response = new FormResponse(user, form);

        for (Task task : form.getTasks()) {
            String rawAnswer;

            if (task.getType().equals("multi")) {
                StringJoiner joiner = new StringJoiner(" ");
                List<String> parameterNames = Collections.list(request.getParameterNames());
                for (String parameter : parameterNames) {
                    if (parameter.startsWith("task" + task.getId())) {
                        joiner.add(request.getParameter(parameter));
                    }
                }
                rawAnswer = joiner.toString();
            } else {
                rawAnswer = request.getParameter("task" + task.getId().toString());
            }


            if (rawAnswer != null && !rawAnswer.equals("")) {
                Answer answer = new Answer(user, task, rawAnswer);
                answerRepository.save(answer);
                response.addAnswer(answer);
            }
        }

        responseRepository.save(response);

        return "redirect:/form_response/" + response.getId();
    }


    @GetMapping("/form/{id}/responses")
    public String formResponses(@PathVariable Long id, Model model) {
        Form form = formService.getFormById(id);
        List<FormResponse> responses = formService.findByForm(form);
        model.addAttribute("responses", responses);
        return "form_answers";
    }

    @RequestMapping("/form/{id}")
    public String taskInfo(@PathVariable Long id, Model model) {
        ifAuthorized(model);
        Form form = formService.getFormById(id);
        model.addAttribute("form", form);
        model.addAttribute("creatorUsername", userRepository.findByUid(form.getCreatorId()).getUsername());
        return "form";
    }

    @GetMapping("/generate")
    public String generateForm(Model model) {
        return "generate";
    }

    @PostMapping("/generate")
    public String formGenerated(
            @RequestParam("numberOfTask") int numberOfTask,
            @RequestParam("level") String level,
            @RequestParam("tag1") String tag1,
            @RequestParam("tag2") String tag2,
            @RequestParam("tag3") String tag3,
            Model model) {

        ifAuthorized(model);
        List<String> tags = Arrays.asList(tag1, tag2, tag3);

        Set<TaskTag> taskTags = new HashSet<>();

        for (var tag : tags) {
            if (tag != null && tagRepository.existsTagByName(tag)) {
                taskTags.addAll(tagRepository.findByName(tag));
            }
        }

        User user = userRepository.findUserByName((String) model.getAttribute("user")).orElseThrow();
        Form form = formGenerator.generate(user, numberOfTask, level, taskTags);

        return "redirect:/form/" + form.getId();
    }

    @PostMapping("/inviteStudent")
    public String inviteStudent(@RequestParam("id") Long id, @RequestParam("username") String username, Model model) {
        userService.addForm(id, username);
        return "redirect:/user";
    }

    @GetMapping("/create_form")
    public String createForm() {
        return "create_form";
    }

    @PostMapping("/create_form")
    public String getCreatedForm(HttpServletRequest request, Model model) {
        ifAuthorized(model);
        String currentUsername = (String) model.getAttribute("user");
        long authorId = userRepository.findUserByName(currentUsername).orElseThrow().getUid();

        ArrayList<String> parameters = Collections.list(request.getParameterNames());
        Form form = new Form(request.getParameter(parameters.get(0)),
                request.getParameter(parameters.get(1)), authorId, false, request.getParameter(parameters.get(2)));
        System.out.println(parameters);
        int n = parameters.size();
        for (int i = 3; i < n; ) {
            String type = request.getParameter(parameters.get(i));
            i++;
            String question = request.getParameter(parameters.get(i));
            i++;
            switch (type) {
                case "1" -> { // Plain text
                    String answer = request.getParameter(parameters.get(i));
                    i++;
                    form.addTask(new Task(question, "", answer, authorId, false, true));
                }
                case "2" -> { // Single choice
                    int answer = -1;
                    JSONObject options = new JSONObject();
                    for (; i < n && !(parameters.get(i).startsWith("type")); ++i) {
                        if (parameters.get(i).startsWith("answer")) {
                            answer = Integer.parseInt(request.getParameter(parameters.get(i)));
                        } else {
                            options.append("options", request.getParameter(parameters.get(i)));
                        }
                    }
                    form.addTask(new SingleChoiceTask(question, "", null, authorId, false, true, answer, options.toString()));
                }
                case "3" -> { // Multi choice
                    StringBuilder answer = new StringBuilder();
                    JSONObject options = new JSONObject();
                    for (; i < n && !(parameters.get(i).startsWith("type")); ++i) {
                        if (parameters.get(i).startsWith("answer")) {
                            answer.append(request.getParameter(parameters.get(i)));
                            answer.append(" ");
                        } else {
                            options.append("options", request.getParameter(parameters.get(i)));
                        }
                    }
                    form.addTask(new MultiChoiceTask(question, "", null, authorId, false, true, answer.toString(), options.toString()));
                }
                default -> throw new RuntimeException("Invalid type of task");
            }
        }
        formService.addForm(form);

        return "redirect:/user";
    }
}
