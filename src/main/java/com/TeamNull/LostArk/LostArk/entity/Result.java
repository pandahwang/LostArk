package com.TeamNull.LostArk.LostArk.entity;

import com.TeamNull.LostArk.LostArk.Job.TopFactor;
import com.TeamNull.LostArk.LostArk.Job.TopFactorConverter;
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

    @Convert(converter = TopFactorConverter.class)
    @Column(name = "TopFactor1", length = 255)
    private TopFactor topFactor1;

    @Convert(converter = TopFactorConverter.class)
    @Column(name = "TopFactor2", length = 255)
    private TopFactor topFactor2;

    @Convert(converter = TopFactorConverter.class)
    @Column(name = "TopFactor3", length = 255)
    private TopFactor topFactor3;

    @Convert(converter = TopFactorConverter.class)
    @Column(name = "TopFactor4", length = 255)
    private TopFactor topFactor4;

    @Convert(converter = TopFactorConverter.class)
    @Column(name = "TopFactor5", length = 255)
    private TopFactor topFactor5;
}
