package com.TeamNull.LostArk.LostArk.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class OuterDataDto {
    private int id;
    private int berserker;
    private int destroyer;
    private int gunlancer;
    private int paladin;
    private int slayer;
    private int arcanist;
    private int summoner;
    private int bard;
    private int sorceress;
    private int wardancer;
    private int scrapper;
    private int soulfist;
    private int glaivier;
    private int striker;
    private int breaker;
    private int deathblade;
    private int shadowhunter;
    private int reaper;
    private int souleater;
    private int sharpshooter;
    private int deadeye;
    private int artillerist;
    private int aeromancer;
    private int machinist;
    private int gunslinger;
    private int artist;
    private Timestamp createdAt;
}
