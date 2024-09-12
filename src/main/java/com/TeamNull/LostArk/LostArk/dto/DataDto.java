package com.TeamNull.LostArk.LostArk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataDto {

    private String name;
    private double percentage;
    private String icon;

    public DataDto(String name, int value , Long total, String icon) {
        double percentage =  (double) value / total * 100;
        this.name = name;
        this.percentage = (double) (Math.round(percentage * 100) / 100.0);
        this.icon = icon;
    }

//    private int id;
//    private int berserker;
//    private int destroyer;
//    private int gunlancer;
//    private int paladin;
//    private int slayer;
//    private int arcanist;
//    private int summoner;
//    private int bard;
//    private int sorceress;
//    private int wardancer;
//    private int scrapper;
//    private int soulfist;
//    private int glaivier;
//    private int striker;
//    private int deathblade;
//    private int shadowhunter;
//    private int reaper;
//    private int sharpshooter;
//    private int deadeye;
//    private int artillerist;
//    private int aeromancer;
//    private int machinist;
//    private int gunslinger;
//    private Timestamp createdAt;

}
