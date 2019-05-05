package com.d0tplist.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Locale {
    @SerializedName("locale")
    @Expose
    private String locale;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("webLanguage")
    @Expose
    private String webLanguage;
    @SerializedName("webRegion")
    @Expose
    private String webRegion;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getWebLanguage() {
        return webLanguage;
    }

    public void setWebLanguage(String webLanguage) {
        this.webLanguage = webLanguage;
    }

    public String getWebRegion() {
        return webRegion;
    }

    public void setWebRegion(String webRegion) {
        this.webRegion = webRegion;
    }

    @Override
    public String toString() {
        return "Locale{" +
                "locale='" + locale + '\'' +
                ", region='" + region + '\'' +
                ", webLanguage='" + webLanguage + '\'' +
                ", webRegion='" + webRegion + '\'' +
                '}';
    }
}
