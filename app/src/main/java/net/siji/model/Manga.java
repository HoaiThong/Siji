package net.siji.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Sun on 7/25/2018.
 */

public class Manga implements Serializable {
    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String otherName;
    @Expose
    private String tblName;
    @Expose
    private String author;
    @Expose
    private String url;
    @Expose
    private String kind;
    @Expose
    private String description;
    @Expose
    private String tag;
    @Expose
    private int amountViews;
    @Expose
    private String newChapter;
    @Expose(serialize = false, deserialize = true)
    private Timestamp createAt;
    @Expose(serialize = false, deserialize = true)
    private Timestamp updateAt;
    @Expose(serialize = false, deserialize = false)
    private Timestamp timestamp;
    @Expose(serialize = false, deserialize = false)
    private long diffDay=10;

    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MANGA = "manga";
    public static final String TAG_KEY_SEARCH = "key_search";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_PID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_OTHER_NAME = "otherName";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_URL = "url";
    public static final String TAG_TABLE = "tblName";
    public static final String TAG_TYPE = "type";
    public static final String TAG_COUNT = "count";
    public static final String TAG_VIEW_QUANTITY = "view_count";
    public static final String TAG_AUTHOR = "author";
    public static final String TAG_NEW_CHAP = "new_chap";
    public static final String TAG_UPDATE_AT = "update_at";
    //
    public Manga() {

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

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getTblName() {
        return tblName;
    }

    public void setTblName(String tblName) {
        this.tblName = tblName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getAmountViews() {
        return amountViews;
    }

    public void setAmountViews(int amountViews) {
        this.amountViews = amountViews;
    }

    public String getNewChapter() {
        return newChapter;
    }

    public void setNewChapter(String newChapter) {
        this.newChapter = newChapter;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public long getDiffDay() {
        return diffDay;
    }

    public void setDiffDay(long diffDay) {
        this.diffDay = diffDay;
    }
}
