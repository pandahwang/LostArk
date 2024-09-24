package com.TeamNull.LostArk.LostArk.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "Comments")
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private int id;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "ID")
    private User user;

    @Column(name = "Password", length = 255)
    @JsonIgnore
    private String password;

    @Column(name = "CreatedAt")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "TopFactorResult", length = 255)
    private String topFactorResult;

    @Column(name = "Content", columnDefinition = "TEXT")
    private String content;

    @Column(name="nickname")
    private String nickName;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private Timestamp updatedAt;

}
