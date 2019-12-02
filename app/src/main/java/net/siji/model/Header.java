package net.siji.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

public class Header implements Parcelable {
    Comic comic;
    int id;
    String bannerUrl;
    int isShowing;
    Timestamp createAt;
    public String TAG_BANNER_URL="bannerUrl";

    public Header() {
    }

    protected Header(Parcel in) {
        comic = in.readParcelable(Comic.class.getClassLoader());
        id = in.readInt();
        bannerUrl = in.readString();
        isShowing = in.readInt();
    }

    public static final Creator<Header> CREATOR = new Creator<Header>() {
        @Override
        public Header createFromParcel(Parcel in) {
            return new Header(in);
        }

        @Override
        public Header[] newArray(int size) {
            return new Header[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(comic, flags);
        dest.writeInt(id);
        dest.writeString(bannerUrl);
        dest.writeInt(isShowing);
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public int getIsShowing() {
        return isShowing;
    }

    public void setIsShowing(int isShowing) {
        this.isShowing = isShowing;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }


}
