package net.siji.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comment implements Serializable {
    Customer customer;
    Comic comic;
    long id;
    String comment;
    Timestamp createAt;
    Timestamp updateAt;

    public Comment() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String toJSON(){
        String json="{\"idComic\": "+comic.getId()+",\"idCustomer\": "+customer.getId()+", \"cmtCustomer\":\""+comment+"\"}";
        return json;
    }
}
