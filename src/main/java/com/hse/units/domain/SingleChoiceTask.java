package com.hse.units.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("SingleChoice")
public class SingleChoiceTask extends Task {

    private String options;

    private int correctChoice;

    public SingleChoiceTask(String title, String body, String answer, long author, boolean standalone, boolean checkable, int correctChoice,
                            String options) {
        super(title, body, answer, author, standalone, checkable);
        this.correctChoice = correctChoice;
        this.options = options;
    }
}
