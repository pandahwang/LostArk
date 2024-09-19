package com.TeamNull.LostArk.LostArk.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
    @JoinColumn(name = "user", referencedColumnName = "ID")
    private User name;

    @Column(name = "Password", length = 255)
    private String password;

    @Column(name = "CreatedAt")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "TopFactorResult", length = 255)
    private String result;

    @Column(name = "Content", columnDefinition = "TEXT")
    private String content;

}
