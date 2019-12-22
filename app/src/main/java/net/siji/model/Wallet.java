package net.siji.model;

import java.sql.Timestamp;

public class Wallet {
    private int id;
    private int statusVip;
    private float coinSum;
    private float coinRewasd;
    private Timestamp createAt;

    public Wallet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatusVip() {
        return statusVip;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public void setStatusVip(int statusVip) {
        this.statusVip = statusVip;
    }

    public float getCoinSum() {
        return coinSum;
    }

    public void setCoinSum(float coinSum) {
        this.coinSum = coinSum;
    }

    public float getCoinRewasd() {
        return coinRewasd;
    }

    public void setCoinRewasd(float coinRewasd) {
        this.coinRewasd = coinRewasd;
    }
}
