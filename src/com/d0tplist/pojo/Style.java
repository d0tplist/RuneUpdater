package com.d0tplist.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Style {

    @SerializedName("allowedSubStyles")
    @Expose
    private List<Integer> allowedSubStyles = null;
    @SerializedName("assetMap")
    @Expose
    private AssetMap assetMap;
    @SerializedName("defaultPageName")
    @Expose
    private String defaultPageName;
    @SerializedName("defaultPerks")
    @Expose
    private List<Integer> defaultPerks = null;
    @SerializedName("defaultSubStyle")
    @Expose
    private Integer defaultSubStyle;
    @SerializedName("iconPath")
    @Expose
    private String iconPath;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slots")
    @Expose
    private List<Slot> slots = null;
    @SerializedName("subStyleBonus")
    @Expose
    private List<SubStyleBonu> subStyleBonus = null;
    @SerializedName("tooltip")
    @Expose
    private String tooltip;

    public List<Integer> getAllowedSubStyles() {
        return allowedSubStyles;
    }

    public void setAllowedSubStyles(List<Integer> allowedSubStyles) {
        this.allowedSubStyles = allowedSubStyles;
    }

    public AssetMap getAssetMap() {
        return assetMap;
    }

    public void setAssetMap(AssetMap assetMap) {
        this.assetMap = assetMap;
    }

    public String getDefaultPageName() {
        return defaultPageName;
    }

    public void setDefaultPageName(String defaultPageName) {
        this.defaultPageName = defaultPageName;
    }

    public List<Integer> getDefaultPerks() {
        return defaultPerks;
    }

    public void setDefaultPerks(List<Integer> defaultPerks) {
        this.defaultPerks = defaultPerks;
    }

    public Integer getDefaultSubStyle() {
        return defaultSubStyle;
    }

    public void setDefaultSubStyle(Integer defaultSubStyle) {
        this.defaultSubStyle = defaultSubStyle;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public List<SubStyleBonu> getSubStyleBonus() {
        return subStyleBonus;
    }

    public void setSubStyleBonus(List<SubStyleBonu> subStyleBonus) {
        this.subStyleBonus = subStyleBonus;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

}
