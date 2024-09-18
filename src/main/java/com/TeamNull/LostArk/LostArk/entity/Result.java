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

    @Column(name = "TopFactor1", length = 255)
    private TopFactor topFactor1;

    @Column(name = "TopFactor2", length = 255)
    private TopFactor topFactor2;

    @Column(name = "TopFactor3", length = 255)
    private TopFactor topFactor3;

    @Column(name = "TopFactor4", length = 255)
    private TopFactor topFactor4;

    @Column(name = "TopFactor5", length = 255)
    private TopFactor topFactor5;

}