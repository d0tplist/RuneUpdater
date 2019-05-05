package com.d0tplist.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Session {

    @SerializedName("accountId")
    @Expose
    private Integer accountId;
    @SerializedName("connected")
    @Expose
    private Boolean connected;
    @SerializedName("error")
    @Expose
    private Object error;
    @SerializedName("gasToken")
    @Expose
    private Object gasToken;
    @SerializedName("idToken")
    @Expose
    private String idToken;
    @SerializedName("isNewPlayer")
    @Expose
    private Boolean isNewPlayer;
    @SerializedName("puuid")
    @Expose
    private String puuid;
    @SerializedName("queueStatus")
    @Expose
    private Object queueStatus;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("summonerId")
    @Expose
    private Integer summonerId;
    @SerializedName("userAuthToken")
    @Expose
    private String userAuthToken;
    @SerializedName("username")
    @Expose
    private String username;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Object getGasToken() {
        return gasToken;
    }

    public void setGasToken(Object gasToken) {
        this.gasToken = gasToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public Boolean getIsNewPlayer() {
        return isNewPlayer;
    }

    public void setIsNewPlayer(Boolean isNewPlayer) {
        this.isNewPlayer = isNewPlayer;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public Object getQueueStatus() {
        return queueStatus;
    }

    public void setQueueStatus(Object queueStatus) {
        this.queueStatus = queueStatus;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(Integer summonerId) {
        this.summonerId = summonerId;
    }

    public String getUserAuthToken() {
        return userAuthToken;
    }

    public void setUserAuthToken(String userAuthToken) {
        this.userAuthToken = userAuthToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Session{" +
                "accountId=" + accountId +
                ", connected=" + connected +
                ", error=" + error +
                ", gasToken=" + gasToken +
                ", idToken='" + idToken + '\'' +
                ", isNewPlayer=" + isNewPlayer +
                ", puuid='" + puuid + '\'' +
                ", queueStatus=" + queueStatus +
                ", state='" + state + '\'' +
                ", summonerId=" + summonerId +
                ", userAuthToken='" + userAuthToken + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
