package com.TeamNull.LostArk.LostArk.entity;

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
    private String topFactor1;

    @Column(name = "TopFactor2", length = 255)
    private String topFactor2;

    @Column(name = "TopFactor3", length = 255)
    private String topFactor3;

    @Column(name = "TopFactor4", length = 255)
    private String topFactor4;

    @Column(name = "TopFactor5", length = 255)
    private String topFactor5;
}
