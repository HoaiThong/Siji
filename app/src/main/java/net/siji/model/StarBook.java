package net.siji.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.sql.Timestamp;

public class StarBook implements Serializable {
    @Expose
    private int id;
    @Expose
    private int idCustomer;
    @Expose
    private int idBook;
    @Expose
    private float valueStar;
    @Expose(serialize = false)
    private Timestamp createAt;
    @Expose(serialize = false)
    private Timestamp updateAt;

    public StarBook() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public float getValueStar() {
        return valueStar;
    }

    public void setValueStar(float valueStar) {
        this.valueStar = valueStar;
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
