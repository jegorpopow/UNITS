package com.hse.units.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("SingleChoice")
public class SingleChoiceTask extends Task {

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

    private int correctChoice;

    public SingleChoiceTask(String title, String body, String answer, long author, boolean standalone, boolean checkable, int correctChoice,
                            String options) {
        super(title, body, answer, author, standalone, checkable);
        this.correctChoice = correctChoice;
        this.options = options;
    }

    @Override
    public boolean checkCorrectness(String receivedAnswer) {
        return this.separatedOptions().get(correctChoice - 1).equals(receivedAnswer);
    }
}
