package net.siji.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.sql.Timestamp;

public class Book implements Serializable {
    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String author;
    @Expose
    private String summary;
    @Expose
    private String country;
    @Expose
    private String tag;
    @Expose
    private String icon;
    @Expose
    private float price;
    @Expose
    private float scoreRating;
    @Expose
    private int viewQuantity;
    @Expose
    private int viewWeek;
    @Expose
    private int viewMonth;
    @Expose
    private String namsx;
    @Expose
    private String nxb;
    @Expose(serialize = false, deserialize = true)
    private Timestamp createAt;
    @Expose(serialize = false, deserialize = true)
    private Timestamp updateAt;

    public Book() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getScoreRating() {
        return scoreRating;
    }

    public void setScoreRating(float scoreRating) {
        this.scoreRating = scoreRating;
    }

    public int getViewQuantity() {
        return viewQuantity;
    }

    public void setViewQuantity(int viewQuantity) {
        this.viewQuantity = viewQuantity;
    }

    public int getViewWeek() {
        return viewWeek;
    }

    public void setViewWeek(int viewWeek) {
        this.viewWeek = viewWeek;
    }

    public int getViewMonth() {
        return viewMonth;
    }

    public void setViewMonth(int viewMonth) {
        this.viewMonth = viewMonth;
    }

    public String getNamsx() {
        return namsx;
    }

    public void setNamsx(String namsx) {
        this.namsx = namsx;
    }

    public String getNxb() {
        return nxb;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }
}
