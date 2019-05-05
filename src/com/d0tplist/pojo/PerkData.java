package com.d0tplist.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PerkData {

    @SerializedName("iconPath")
    @Expose
    private String iconPath;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("longDesc")
    @Expose
    private String longDesc;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("shortDesc")
    @Expose
    private String shortDesc;
    @SerializedName("tooltip")
    @Expose
    private String tooltip;

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

}

