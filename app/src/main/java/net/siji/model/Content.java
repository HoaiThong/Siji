package net.siji.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.sql.Timestamp;

public class Content implements Serializable {
    @Expose
    private int id;
    @Expose
    private int idBook;

    // số thứ tự
    @Expose
    private int serial;
    @Expose
    private String name;
    @Expose
    private String content;
    @Expose(serialize = false)
    private Timestamp createAt;
    @Expose(serialize = false)
    private Timestamp updateAt;

    public Content() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
