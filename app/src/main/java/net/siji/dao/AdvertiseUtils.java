package net.siji.dao;

import net.siji.model.Advertise;
import net.siji.model.Comic;

import org.json.JSONException;
import org.json.JSONObject;

public class AdvertiseUtils {
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public String TAG_ID = "id";
    public String TAG_IMG_URL = "imgUrl";
    public String TAG_TARGET_URL = "targetUrl";
    public String TAG_DESCRIPTION = "description";
    public String TAG_IMG_WIDTH = "widthImg";
    public String TAG_IMG_HEIGHT = "heightImg";
    public static final String TAG_ADVERTISE = "advertise";

    public Advertise createFromJSONObject(JSONObject jsonObject) {
        Advertise a=new Advertise();
        try {
            if (!jsonObject.isNull(TAG_ID)) a.setId(jsonObject.getInt(TAG_ID));
            if (!jsonObject.isNull(TAG_IMG_URL)) a.setImgUrl(jsonObject.getString(TAG_IMG_URL));
            if (!jsonObject.isNull(TAG_DESCRIPTION)) a.setDescription(jsonObject.getString(TAG_DESCRIPTION));
            if (!jsonObject.isNull(TAG_TARGET_URL)) a.setTargetUrl(jsonObject.getString(TAG_TARGET_URL));
            if (!jsonObject.isNull(TAG_IMG_HEIGHT)) a.setHeightImg(jsonObject.getInt(TAG_IMG_HEIGHT));
            if (!jsonObject.isNull(TAG_IMG_WIDTH)) a.setWidthImg(jsonObject.getInt(TAG_IMG_WIDTH));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return a;
    }
}
