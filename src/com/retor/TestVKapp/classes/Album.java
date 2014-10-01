package com.retor.TestVKapp.classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Антон on 26.09.2014.
 */
public class Album {
    public int id;
    public Thumb thumb;
    public int owner_id;
    public String title;
    public String description;
    public long created;
    public long updated;
    public int size;
    public Link link;

    public Album(){}

    public static Album parse(JSONObject object){
        Album out_album = new Album();
        try {
            out_album.id = object.getInt("id");
            out_album.owner_id = object.getInt("owner_id");
            out_album.title = object.getString("title");
            out_album.description = object.getString("description");
            out_album.created = object.getLong("created");
            out_album.updated = object.getLong("updated");
            out_album.size = object.getInt("size");
            //create thumb
            if (object.optJSONObject("thumb")!=null){
                Thumb thum = Thumb.parse(object.getJSONObject("thumb"));
                out_album.thumb = thum;
            }
            if (object.optJSONObject("link")!=null){
                Link link1 = Link.parse(object.getJSONObject("link"));
                out_album.link = link1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out_album;
    }


    public  static class Thumb{
        public int id;
        public int album_id;
        public int owner_id;
        public int user_id;
        public String photo_75;
        public String photo_130;
        public String photo_604;
        public String photo_807;
        public String photo_1280;
        public long date;
        public String access_key;

        public Thumb(){}

        public static Thumb parse(JSONObject o){
            Thumb out_thumb = new Thumb();
            try {
                out_thumb.id = o.getInt("id");
                out_thumb.album_id = o.getInt("album_id");
                out_thumb.owner_id = o.getInt("owner_id");
                out_thumb.user_id = o.getInt("user_id");
                out_thumb.photo_75 = o.getString("photo_75");
                out_thumb.photo_130 = o.getString("photo_130");
                out_thumb.photo_604 = o.getString("photo_604");
                out_thumb.photo_807 = o.getString("photo_807");
                out_thumb.photo_1280 = o.getString("photo_1280");
                out_thumb.date = o.getLong("date");
                out_thumb.access_key = o.optString("access_key");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return out_thumb;
        }
    }
}
