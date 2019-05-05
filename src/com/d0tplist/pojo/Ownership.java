package com.d0tplist.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ownership {

    @SerializedName("freeToPlayReward")
    @Expose
    private Boolean freeToPlayReward;
    @SerializedName("owned")
    @Expose
    private Boolean owned;
    @SerializedName("rental")
    @Expose
    private Rental rental;

    public Boolean getFreeToPlayReward() {
        return freeToPlayReward;
    }

    public void setFreeToPlayReward(Boolean freeToPlayReward) {
        this.freeToPlayReward = freeToPlayReward;
    }

    public Boolean getOwned() {
        return owned;
    }

    public void setOwned(Boolean owned) {
        this.owned = owned;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

}
