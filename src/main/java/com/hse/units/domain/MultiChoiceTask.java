package com.hse.units.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("MultiChoice")
public class MultiChoiceTask extends Task {

    private String options;

    private String correctChoices;

    public MultiChoiceTask(String title, String body, String answer, long author, boolean standalone, boolean checkable, String correctChoices,
                           String options) {
        super(title, body, answer, author, standalone, checkable);
        this.correctChoices = correctChoices;
        this.options = options;
    }
}
