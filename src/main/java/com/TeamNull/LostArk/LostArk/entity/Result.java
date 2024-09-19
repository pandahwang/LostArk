package com.TeamNull.LostArk.LostArk.entity;

import com.TeamNull.LostArk.LostArk.Job.TopFactor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Results")
@Getter
@Setter
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "ID", nullable = false)
    private User user;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "jobName", column = @Column(name = "TopFactor1_jobName")),
            @AttributeOverride(name = "value", column = @Column(name = "TopFactor1_value"))
    })
    private TopFactor topFactor1;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "jobName", column = @Column(name = "TopFactor2_jobName")),
            @AttributeOverride(name = "value", column = @Column(name = "TopFactor2_value"))
    })
    private TopFactor topFactor2;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "jobName", column = @Column(name = "TopFactor3_jobName")),
            @AttributeOverride(name = "value", column = @Column(name = "TopFactor3_value"))
    })
    private TopFactor topFactor3;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "jobName", column = @Column(name = "TopFactor4_jobName")),
            @AttributeOverride(name = "value", column = @Column(name = "TopFactor4_value"))
    })
    private TopFactor topFactor4;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "jobName", column = @Column(name = "TopFactor5_jobName")),
            @AttributeOverride(name = "value", column = @Column(name = "TopFactor5_value"))
    })
    private TopFactor topFactor5;

}