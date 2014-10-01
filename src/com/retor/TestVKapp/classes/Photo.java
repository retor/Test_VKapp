package com.retor.TestVKapp.classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Антон on 26.09.2014.
 */
public class Photo {

    public int id;
    public int album_id;
    public int owner_id;
    public int user_id;
    public String photo_75;
    public String photo_130;
    public String photo_604;
    public String photo_807;
    public String text;//: '',
    public long date;
    public int post_id;
    private String access_key;

    public Photo(){}

    public static Photo parse(JSONObject o){
        Photo out = new Photo();
        try {
            out.id = o.getInt("id");
            out.album_id = o.getInt("album_id");
            out.owner_id = o.getInt("owner_id");
            out.user_id = o.optInt("user_id");
            out.photo_75 = o.getString("photo_75");
            out.photo_130 = o.getString("photo_130");
            out.photo_604 = o.getString("photo_604");
            out.photo_807 = o.getString("photo_807");
            out.text = o.getString("text");
            out.date = o.optLong("date");
            out.post_id = o.getInt("post_id");
            out.access_key = o.optString("access_key");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }

    public String getAccess_key() {
        return access_key;
    }
}
