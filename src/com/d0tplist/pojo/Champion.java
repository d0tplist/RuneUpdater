package com.d0tplist.pojo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Champion {

    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("banVoPath")
    @Expose
    private String banVoPath;
    @SerializedName("baseLoadScreenPath")
    @Expose
    private String baseLoadScreenPath;
    @SerializedName("botEnabled")
    @Expose
    private Boolean botEnabled;
    @SerializedName("chooseVoPath")
    @Expose
    private String chooseVoPath;
    @SerializedName("disabledQueues")
    @Expose
    private List<String> disabledQueues = null;
    @SerializedName("freeToPlay")
    @Expose
    private Boolean freeToPlay;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ownership")
    @Expose
    private Ownership ownership;
    @SerializedName("purchased")
    @Expose
    private Long purchased;
    @SerializedName("rankedPlayEnabled")
    @Expose
    private Boolean rankedPlayEnabled;
    @SerializedName("roles")
    @Expose
    private List<String> roles = null;
    @SerializedName("squarePortraitPath")
    @Expose
    private String squarePortraitPath;
    @SerializedName("stingerSfxPath")
    @Expose
    private String stingerSfxPath;
    @SerializedName("title")
    @Expose
    private String title;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getBanVoPath() {
        return banVoPath;
    }

    public void setBanVoPath(String banVoPath) {
        this.banVoPath = banVoPath;
    }

    public String getBaseLoadScreenPath() {
        return baseLoadScreenPath;
    }

    public void setBaseLoadScreenPath(String baseLoadScreenPath) {
        this.baseLoadScreenPath = baseLoadScreenPath;
    }

    public Boolean getBotEnabled() {
        return botEnabled;
    }

    public void setBotEnabled(Boolean botEnabled) {
        this.botEnabled = botEnabled;
    }

    public String getChooseVoPath() {
        return chooseVoPath;
    }

    public void setChooseVoPath(String chooseVoPath) {
        this.chooseVoPath = chooseVoPath;
    }

    public List<String> getDisabledQueues() {
        return disabledQueues;
    }

    public void setDisabledQueues(List<String> disabledQueues) {
        this.disabledQueues = disabledQueues;
    }

    public Boolean getFreeToPlay() {
        return freeToPlay;
    }

    public void setFreeToPlay(Boolean freeToPlay) {
        this.freeToPlay = freeToPlay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ownership getOwnership() {
        return ownership;
    }

    public void setOwnership(Ownership ownership) {
        this.ownership = ownership;
    }

    public Long getPurchased() {
        return purchased;
    }

    public void setPurchased(Long purchased) {
        this.purchased = purchased;
    }

    public Boolean getRankedPlayEnabled() {
        return rankedPlayEnabled;
    }

    public void setRankedPlayEnabled(Boolean rankedPlayEnabled) {
        this.rankedPlayEnabled = rankedPlayEnabled;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getSquarePortraitPath() {
        return squarePortraitPath;
    }

    public void setSquarePortraitPath(String squarePortraitPath) {
        this.squarePortraitPath = squarePortraitPath;
    }

    public String getStingerSfxPath() {
        return stingerSfxPath;
    }

    public void setStingerSfxPath(String stingerSfxPath) {
        this.stingerSfxPath = stingerSfxPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return name;
    }
}
