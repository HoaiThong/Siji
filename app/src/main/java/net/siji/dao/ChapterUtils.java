package net.siji.dao;

import net.siji.model.Chapter;

import org.json.JSONException;
import org.json.JSONObject;

public class ChapterUtils {
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_CHAPTER = "chapter";
    public static final String TAG_PAGE = "page";
    public static final String TAG_URL = "url";
    public static final String TAG_PRICE = "price";
    public static final String TAG_TRANSLATOR = "translator";

    public Chapter convertFromJSONObject(JSONObject jsonObject) {
        Chapter c = new Chapter();

        try {
            if (!jsonObject.isNull(TAG_ID)) c.setId(jsonObject.getInt(TAG_ID));
            if (!jsonObject.isNull(TAG_NAME)) c.setName(jsonObject.getString(TAG_NAME));
            if (!jsonObject.isNull(TAG_CHAPTER))
                c.setChapter((float) jsonObject.getDouble(TAG_CHAPTER));
            if (!jsonObject.isNull(TAG_PAGE)) c.setPage(jsonObject.getInt(TAG_PAGE));
            if (!jsonObject.isNull(TAG_PRICE)) c.setPrice((float) jsonObject.getDouble(TAG_PRICE));
            if (!jsonObject.isNull("chapterComic")) {
                c.setPrice((float) jsonObject.getDouble("realprice"));
            } else c.setPrice(0);
            if (!jsonObject.isNull(TAG_URL)) c.setUrl(jsonObject.getString(TAG_URL));
            if (!jsonObject.isNull(TAG_TRANSLATOR))
                c.setTranslator(jsonObject.getString(TAG_TRANSLATOR));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return c;
    }
}
