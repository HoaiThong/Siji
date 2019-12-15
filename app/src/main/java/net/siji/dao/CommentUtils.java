package net.siji.dao;

import net.siji.model.Comic;
import net.siji.model.Comment;
import net.siji.model.Customer;

import org.json.JSONException;
import org.json.JSONObject;

public class CommentUtils {
    public static final String TAG_ID = "id";
    public static final String TAG_ID_CUSTOMER = "idCustomer";
    public static final String TAG_ID_COMIC = "idComic";
    public static final String TAG_COMMENT = "cmtCustomer";
    public static final String TAG_NAME_GOOGLE = "nameGoogle";
    public static final String TAG_NAME_FACEBOOK = "nameFaceBook";
    public static final String TAG_ICON_URL = "iconUrl";

    public Comment createFromJSONObject(JSONObject jsonObject) {
        Comment c = new Comment();
        Customer user = new Customer();
        try {
            if (!jsonObject.isNull(TAG_ID)) c.setId(jsonObject.getInt(TAG_ID));
            if (!jsonObject.isNull(TAG_NAME_GOOGLE))
                user.setNameGoogle(jsonObject.getString(TAG_NAME_GOOGLE));
            if (!jsonObject.isNull(TAG_NAME_FACEBOOK))
                user.setNameFaceBook(jsonObject.getString(TAG_NAME_FACEBOOK));
            if (!jsonObject.isNull(TAG_ICON_URL))
                user.setIconUrl(jsonObject.getString(TAG_ICON_URL));
            c.setCustomer(user);
            if (!jsonObject.isNull(TAG_ID_COMIC)) {
                Comic comic = new Comic();
                comic.setId(jsonObject.getInt(TAG_ID_COMIC));
                c.setComic(comic);
            }
            if (!jsonObject.isNull(TAG_COMMENT)) c.setComment(jsonObject.getString(TAG_COMMENT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return c;
    }
}
