package com.TeamNull.LostArk.LostArk.Job;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class JobAttributes {
    private final String jobName;
    private final int Agreeableness;
    private final int conscientiousness;
    private final int extraversion;
    private final int openness;
    private final int neuroticism;
    private final String color;
    private final String icon;

    @JsonCreator
    public JobAttributes(
            @JsonProperty("jobName") String jobName,
            @JsonProperty("Agreeableness") int Agreeableness,
            @JsonProperty("conscientiousness") int conscientiousness,
            @JsonProperty("extraversion") int extraversion,
            @JsonProperty("openness") int openness,
            @JsonProperty("neuroticism") int neuroticism,
            @JsonProperty("color") String color,
            @JsonProperty("icon") String icon) {
        this.jobName = jobName;
        this.Agreeableness = Agreeableness;
        this.conscientiousness = conscientiousness;
        this.extraversion = extraversion;
        this.openness = openness;
        this.neuroticism = neuroticism;
        this.color = color;
        this.icon = icon;
    }
}
