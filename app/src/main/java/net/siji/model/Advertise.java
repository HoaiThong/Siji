package net.siji.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Advertise implements Serializable {
    public int id;
    private String imgUrl;
    private String description;
    private String targetUrl;

    private String title;
    private String mimeType;
    private int widthImg;
    private int heightImg;
    private Timestamp createAt;

    public String TAG_ID="id";
    public String TAG_IMG_URL="imgUrl";
    public String TAG_TITLE="title";
    // mimeType: image/text
    public String TAG_MIME_TYPE="mimeType";
    public String TAG_TARGET_URL="targetUrl";
    public String TAG_DESCRIPTION="description";
    public String TAG_IMG_WIDTH="widthImg";
    public String TAG_IMG_HEIGHT="heightImg";

    public Advertise() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public int getWidthImg() {
        return widthImg;
    }

    public void setWidthImg(int widthImg) {
        this.widthImg = widthImg;
    }

    public int getHeightImg() {
        return heightImg;
    }

    public void setHeightImg(int heightImg) {
        this.heightImg = heightImg;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

}
