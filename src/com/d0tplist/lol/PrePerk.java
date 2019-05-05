package com.d0tplist.lol;

import java.util.ArrayList;
import java.util.List;

public class PrePerk {

    private Integer primaryStyle;
    private Integer secondaryStyle;

    private final List<Integer> primaryPerks = new ArrayList<>();
    private final List<Integer> secondaryPerks = new ArrayList<>();
    private final List<Integer> shardPerks = new ArrayList<>();

    private String pickRate;
    private String winRate;
    private String type;

    public PrePerk() {
    }

    public List<Integer> getPrimaryPerks() {
        return primaryPerks;
    }

    public List<Integer> getSecondaryPerks() {
        return secondaryPerks;
    }

    public List<Integer> getShardPerks() {
        return shardPerks;
    }

    public Integer getPrimaryStyle() {
        return primaryStyle;
    }

    public void setPrimaryStyle(Integer primaryStyle) {
        this.primaryStyle = primaryStyle;
    }

    public Integer getSecondaryStyle() {
        return secondaryStyle;
    }

    public void setSecondaryStyle(Integer secondaryStyle) {
        this.secondaryStyle = secondaryStyle;
    }

    public String getPickRate() {
        return pickRate;
    }

    public void setPickRate(String pickRate) {
        this.pickRate = pickRate;
    }

    public String getWinRate() {
        return winRate;
    }

    public void setWinRate(String winRate) {
        this.winRate = winRate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PrePerk{" +
                "primaryStyle=" + primaryStyle +
                ", secondaryStyle=" + secondaryStyle +
                ", primaryPerks=" + primaryPerks +
                ", secondaryPerks=" + secondaryPerks +
                ", shardPerks=" + shardPerks +
                ", pickRate='" + pickRate + '\'' +
                ", winRate='" + winRate + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
