package com.TeamNull.LostArk.LostArk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "Data")
@Getter
@Setter
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "Berserker")
    private int berserker;

    @Column(name = "Destroyer")
    private int destroyer;

    @Column(name = "Gunlancer")
    private int gunlancer;

    @Column(name = "Paladin")
    private int paladin;

    @Column(name = "Slayer")
    private int slayer;

    @Column(name = "Arcanist")
    private int arcanist;

    @Column(name = "Summoner")
    private int summoner;

    @Column(name = "Bard")
    private int bard;

    @Column(name = "Sorceress")
    private int sorceress;

    @Column(name = "Wardancer")
    private int wardancer;

    @Column(name = "Scrapper")
    private int scrapper;

    @Column(name = "Soulfist")
    private int soulfist;

    @Column(name = "Glaivier")
    private int glaivier;

    @Column(name = "Striker")
    private int striker;

    @Column(name = "Breaker")
    private int breaker;

    @Column(name = "Deathblade")
    private int deathblade;

    @Column(name = "Shadowhunter")
    private int shadowhunter;

    @Column(name = "Reaper")
    private int reaper;

    @Column(name = "SoulEater")
    private int souleater;

    @Column(name = "Sharpshooter")
    private int sharpshooter;

    @Column(name = "Deadeye")
    private int deadeye;

    @Column(name = "Artillerist")
    private int artillerist;

    @Column(name = "Aeromancer")
    private int aeromancer;

    @Column(name = "Machinist")
    private int machinist;

    @Column(name = "Gunslinger")
    private int gunslinger;

    @Column(name = "Artist")
    private int artist;

    @Column(name = "CreatedAt")
    private Timestamp createdAt;
}
