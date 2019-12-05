package net.siji.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Sun on 7/27/2018.
 */

public class Chapter implements Serializable {
    private int id;
    private String name;
    private float chapter;
    private String translator;
    private String url;
    private float price;
    private int page;
    Timestamp createAt;

    //
    public Chapter() {

    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getChapter() {
        return chapter;
    }

    public void setChapter(float chapter) {
        this.chapter = chapter;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
