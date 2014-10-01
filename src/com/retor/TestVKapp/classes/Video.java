package com.retor.TestVKapp.classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Антон on 01.10.2014.
 */
public class Video {

    public int id;
    public int owner_id;
    public String title;
    public int duration;
    public String description;
    public String photo_130;
    public String photo_320;
    public String photo_640;
    public long date;
    public int post_id;
    private String access_key;

    public Video() {
    }

    public static Video parse(JSONObject o){
        Video out = new Video();
        try {
            out.id = o.getInt("id");
            out.owner_id = o.getInt("owner_id");
            out.title = o.getString("title");
            out.duration = o.getInt("duration");
            out.description = o.getString("description");
            out.photo_130 = o.getString("photo_130");
            out.photo_320 = o.getString("photo_320");
            out.photo_640 = o.getString("photo_640");
            out.date = o.optLong("date");
            out.post_id = o.optInt("post_id");
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
