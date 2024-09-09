package com.TeamNull.LostArk.LostArk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "Comments")
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "ID")
    private User user;

    @Column(name = "Password", length = 255)
    private String password;

    @Column(name = "CreatedAt")
    private Timestamp createdAt;

    @Column(name = "TopFactorResult", length = 255)
    private String topFactorResult;

    @Column(name = "Content", columnDefinition = "TEXT")
    private String content;

}
