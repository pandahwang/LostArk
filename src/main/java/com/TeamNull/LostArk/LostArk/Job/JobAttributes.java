package com.TeamNull.LostArk.LostArk.Job;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JobAttributes {
    private final String jobName;
    private final int Agreeableness;
    private final int conscientiousness;
    private final int extraversion;
    private final int openness;
    private final int neuroticism;
}
