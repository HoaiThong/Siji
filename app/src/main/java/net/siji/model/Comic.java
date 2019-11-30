package net.siji.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Sun on 7/25/2018.
 */

public class Comic implements Parcelable,Serializable  {
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
    private String iconUrl;
    @Expose
    private String type;
    @Expose
    private String summary;
    @Expose
    private String tag;
    @Expose
    private float scoreRating;
    @Expose
    private int viewWeek;
    @Expose
    private int viewMonth;
    @Expose
    private int viewSum;
    @Expose
    private String newChapter;
    @Expose
    private Timestamp createAt;
    @Expose
    private Timestamp updateAt;

    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MANGA = "manga";
    public static final String TAG_KEY_SEARCH = "key_search";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_PID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_OTHER_NAME = "otherName";
    public static final String TAG_SUMMARY = "summary";
    public static final String TAG_ICON_URL = "iconUrl";
    public static final String TAG_TABLE = "tblName";
    public static final String TAG_TYPE = "type";
    public static final String TAG_COUNT = "count";
    public static final String TAG_VIEW_SUM = "viewSum";
    public static final String TAG_AUTHOR = "author";
    public static final String TAG_NEW_CHAP = "newChapter";
    public static final String TAG_UPDATE_AT = "update_at";
    //
    public Comic() {

    }

    protected Comic(Parcel in) {
        id = in.readInt();
        name = in.readString();
        otherName = in.readString();
        tblName = in.readString();
        author = in.readString();
        iconUrl = in.readString();
        type = in.readString();
        summary = in.readString();
        tag = in.readString();
        scoreRating = in.readFloat();
        viewWeek = in.readInt();
        viewMonth = in.readInt();
        viewSum = in.readInt();
        newChapter = in.readString();
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel in) {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public float getScoreRating() {
        return scoreRating;
    }

    public void setScoreRating(float scoreRating) {
        this.scoreRating = scoreRating;
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

    public int getViewSum() {
        return viewSum;
    }

    public void setViewSum(int viewSum) {
        this.viewSum = viewSum;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(otherName);
        dest.writeString(tblName);
        dest.writeString(author);
        dest.writeString(iconUrl);
        dest.writeString(type);
        dest.writeString(summary);
        dest.writeString(tag);
        dest.writeFloat(scoreRating);
        dest.writeInt(viewWeek);
        dest.writeInt(viewMonth);
        dest.writeInt(viewSum);
        dest.writeString(newChapter);
    }


}
