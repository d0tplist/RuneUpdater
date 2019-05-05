package com.d0tplist.pojo;

import java.util.List;

import com.d0tplist.lol.PrePerk;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Perk {

    @SerializedName("autoModifiedSelections")
    @Expose
    private List<Object> autoModifiedSelections = null;
    @SerializedName("current")
    @Expose
    private Boolean current;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("isDeletable")
    @Expose
    private Boolean isDeletable;
    @SerializedName("isEditable")
    @Expose
    private Boolean isEditable;
    @SerializedName("isValid")
    @Expose
    private Boolean isValid;
    @SerializedName("lastModified")
    @Expose
    private Long lastModified;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("primaryStyleId")
    @Expose
    private Integer primaryStyleId;
    @SerializedName("selectedPerkIds")
    @Expose
    private List<Integer> selectedPerkIds = null;
    @SerializedName("subStyleId")
    @Expose
    private Integer subStyleId;

    private transient PrePerk prePerk;

    public Perk() {
    }

    public Perk(String name) {
        this.name = name;
    }

    public PrePerk getPrePerk() {
        return prePerk;
    }

    public void setPrePerk(PrePerk prePerk) {
        this.prePerk = prePerk;
    }

    public List<Object> getAutoModifiedSelections() {
        return autoModifiedSelections;
    }

    public void setAutoModifiedSelections(List<Object> autoModifiedSelections) {
        this.autoModifiedSelections = autoModifiedSelections;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeletable() {
        return isDeletable;
    }

    public void setIsDeletable(Boolean isDeletable) {
        this.isDeletable = isDeletable;
    }

    public Boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(Boolean isEditable) {
        this.isEditable = isEditable;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getPrimaryStyleId() {
        return primaryStyleId;
    }

    public void setPrimaryStyleId(Integer primaryStyleId) {
        this.primaryStyleId = primaryStyleId;
    }

    public List<Integer> getSelectedPerkIds() {
        return selectedPerkIds;
    }

    public void setSelectedPerkIds(List<Integer> selectedPerkIds) {
        this.selectedPerkIds = selectedPerkIds;
    }

    public Integer getSubStyleId() {
        return subStyleId;
    }

    public void setSubStyleId(Integer subStyleId) {
        this.subStyleId = subStyleId;
    }

    @Override
    public String toString() {

        if (prePerk != null) {
            return prePerk.getType() + " " + prePerk.getWinRate();
        }

        return name;
    }
}
