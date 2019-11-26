package net.siji.model;

import java.io.Serializable;

public class SubjectManga implements Serializable {
    int id;
    String name;
    String url;
    String nameEng;
    public String TAG_ID="id";
    public String TAG_NAME="name";
    public String TAG_URL="url";
    public String TAG_NAME_EN="name_en";

    public SubjectManga() {

    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
