package com.TeamNull.LostArk.LostArk.Job;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class TopFactor {
        private String jobName;
        private double value;

        public TopFactor(String jobName, double value) {
            this.jobName = jobName;
            this.value = value;
        }
}
