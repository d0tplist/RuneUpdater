package com.d0tplist.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubStyleBonu {

    @SerializedName("perkId")
    @Expose
    private Integer perkId;
    @SerializedName("styleId")
    @Expose
    private Integer styleId;

    public Integer getPerkId() {
        return perkId;
    }

    public void setPerkId(Integer perkId) {
        this.perkId = perkId;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

}
