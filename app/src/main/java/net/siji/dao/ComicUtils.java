package net.siji.dao;

import net.siji.model.Comic;

import org.json.JSONException;
import org.json.JSONObject;

public class ComicUtils {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MANGA = "manga";
    public static final String TAG_KEY_SEARCH = "key_search";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_OTHER_NAME = "otherName";
    public static final String TAG_SUMMARY = "summary";
    public static final String TAG_ICON_URL = "iconUrl";
    public static final String TAG_TABLE = "tblName";
    public static final String TAG_TYPE = "type";
    public static final String TAG_VIEW_DAY = "viewDay";
    public static final String TAG_VIEW_WEEK = "viewWeek";
    public static final String TAG_VIEW_MONTH = "viewMonth";
    public static final String TAG_VIEW_SUM = "viewSum";
    public static final String TAG_AUTHOR = "author";
    public static final String TAG_NEW_CHAP = "newChapter";
    public static final String TAG_TAG = "tag";
    public static final String TAG_UPDATE_AT = "update_at";

    public Comic convertFromJSONObject(JSONObject jsonObject){
        Comic c=new Comic();
        try {
            if (!jsonObject.isNull(TAG_ID)) c.setId(jsonObject.getInt(TAG_ID));
            if (!jsonObject.isNull(TAG_NAME)) c.setName(jsonObject.getString(TAG_NAME));
            if (!jsonObject.isNull(TAG_TABLE))
                c.setTblName(jsonObject.getString(TAG_TABLE));
            else c.setTblName("0");
            if (!jsonObject.isNull(TAG_OTHER_NAME)) c.setOtherName(jsonObject.getString(TAG_OTHER_NAME));
            if (!jsonObject.isNull(TAG_SUMMARY)) c.setSummary(jsonObject.getString(TAG_SUMMARY));
            if (!jsonObject.isNull(TAG_ICON_URL)) c.setIconUrl(jsonObject.getString(TAG_ICON_URL));
            if (!jsonObject.isNull(TAG_TYPE))c.setType(jsonObject.getString(TAG_TYPE));
            if (!jsonObject.isNull(TAG_AUTHOR))c.setAuthor(jsonObject.getString(TAG_AUTHOR));
            if ((!jsonObject.isNull(TAG_NEW_CHAP)))c.setNewChapter(jsonObject.getString(TAG_NEW_CHAP));
            if ((!jsonObject.isNull(TAG_TAG)))c.setTag(jsonObject.getString(TAG_TAG));
            if ((!jsonObject.isNull(TAG_VIEW_DAY)))c.setViewDay(jsonObject.getInt(TAG_VIEW_DAY));
            if ((!jsonObject.isNull(TAG_VIEW_MONTH)))c.setViewMonth(jsonObject.getInt(TAG_VIEW_MONTH));
            if ((!jsonObject.isNull(TAG_VIEW_WEEK)))c.setViewWeek(jsonObject.getInt(TAG_VIEW_WEEK));
            if ((!jsonObject.isNull(TAG_VIEW_SUM)))c.setViewSum(jsonObject.getInt(TAG_VIEW_SUM));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return c;
    }
}
