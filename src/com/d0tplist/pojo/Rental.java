package com.d0tplist.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rental {

    @SerializedName("endDate")
    @Expose
    private Long endDate;
    @SerializedName("purchaseDate")
    @Expose
    private Long purchaseDate;
    @SerializedName("rented")
    @Expose
    private Boolean rented;
    @SerializedName("winCountRemaining")
    @Expose
    private Long winCountRemaining;

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Long getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Boolean getRented() {
        return rented;
    }

    public void setRented(Boolean rented) {
        this.rented = rented;
    }

    public Long getWinCountRemaining() {
        return winCountRemaining;
    }

    public void setWinCountRemaining(Long winCountRemaining) {
        this.winCountRemaining = winCountRemaining;
    }

}
