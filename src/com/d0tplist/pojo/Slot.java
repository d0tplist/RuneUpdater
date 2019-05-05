package com.d0tplist.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slot {

    @SerializedName("perks")
    @Expose
    private List<Integer> perks = null;
    @SerializedName("slotLabel")
    @Expose
    private String slotLabel;
    @SerializedName("type")
    @Expose
    private String type;

    public List<Integer> getPerks() {
        return perks;
    }

    public void setPerks(List<Integer> perks) {
        this.perks = perks;
    }

    public String getSlotLabel() {
        return slotLabel;
    }

    public void setSlotLabel(String slotLabel) {
        this.slotLabel = slotLabel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
