package com.hse.units.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@DiscriminatorValue("MultiChoice")
public class MultiChoiceTask extends Task {

    private String options;

    public List<String> separatedOptions() {
        JSONObject obj = new JSONObject(options);
        JSONArray answers = obj.getJSONArray("options");

        List<String> result = new ArrayList<>();
        for (int i = 0; i < answers.length(); i++) {
            result.add(answers.getString(i));
        }

        return result;
    }

    private String correctChoices;

    public MultiChoiceTask(String title, String body, String answer, long author, boolean standalone, boolean checkable, String correctChoices,
                           String options) {
        super(title, body, answer, author, standalone, checkable);
        this.correctChoices = correctChoices;
        this.options = options;
    }

    @Override
    public boolean checkCorrectness(String receivedAnswer) {
        System.out.println("Expected: " + separatedOptions());
        System.out.println("Actual: " + Arrays.stream(receivedAnswer.split(" ")).collect(Collectors.toSet()));

        return Arrays.stream(correctChoices.split(" ")).collect(Collectors.toSet())
                .equals(Arrays.stream(receivedAnswer.split(" ")).collect(Collectors.toSet()));
    }
}
