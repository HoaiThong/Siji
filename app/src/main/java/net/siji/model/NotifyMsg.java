package net.siji.model;

import java.io.Serializable;

public class NotifyMsg implements Serializable {
    int id;
    int id_manga;
    String name_chapter;
    String msg;
    String TAG_ID="id";
    String TAG_ID_MANGA="id_manga";
    String TAG_NAME_CHAPTER="name_chapter";
    String TAG_MSG="msg";

    public NotifyMsg() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_manga() {
        return id_manga;
    }

    public void setId_manga(int id_manga) {
        this.id_manga = id_manga;
    }

    public String getName_chapter() {
        return name_chapter;
    }

    public void setName_chapter(String name_chapter) {
        this.name_chapter = name_chapter;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
