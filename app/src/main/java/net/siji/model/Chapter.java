package net.siji.model;

import java.io.Serializable;

/**
 * Created by Sun on 7/27/2018.
 */

public class Chapter  implements Serializable {
    int id;
    String name;
    String url;
    int page;
    //
    public Chapter() {

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
