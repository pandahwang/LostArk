package com.TeamNull.LostArk.LostArk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    @Column(name = "ID", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "Question1")
    private int question1;

    @Column(name = "Question2")
    private int question2;

    @Column(name = "Question3")
    private int question3;

    @Column(name = "Question4")
    private int question4;

    @Column(name = "Question5")
    private int question5;

    @Column(name = "Question6")
    private int question6;

    @Column(name = "Question7")
    private int question7;

    @Column(name = "Question8")
    private int question8;

    @Column(name = "Question9")
    private int question9;

    @Column(name = "Question10")
    private int question10;

}
