package com.TeamNull.LostArk.LostArk.Job;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopFactor {
        private String jobName;
        private double value;

        public TopFactor(String jobName, double value) {
            this.jobName = jobName;
            this.value = value;
        }
}
