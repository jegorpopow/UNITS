package com.hse.units.controllers;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Controller
public class TaskTimer {


    /**
     * Adds timer to model.
     * Possible code in mapping:
     *      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
     *      var now = LocalDateTime.now().plusSeconds(duration);
     *      model = addTimer(model, dtf.format(now))
     * @param model passed from GetMapping controller
     * @param till String in "yyyy-MM-dd HH:mm:ss" format, which means up to what time the timer will run
     * @return updated model with timer attribute
     */
    public Model addTimer(Model model, String till) {
        try {
            model.addAttribute("endDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(till));
        } catch (ParseException e) {
            System.out.println("ERROR in addTimer");
        }
        return model;
    }

    @GetMapping("/timer")
    public String timer(Model model) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        var now = LocalDateTime.now().plusSeconds(10);
        model = addTimer(model, dtf.format(now));
        return "/timer";
    }
}
