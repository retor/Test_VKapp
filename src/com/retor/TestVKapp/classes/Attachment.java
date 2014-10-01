package com.retor.TestVKapp.classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Антон on 26.09.2014.
 */
public class Attachment {
    public String type;
    public Album album;
    public Photo photo;
    public Link link;
    public Video video;

    public Attachment(){}

    public static Attachment parse(JSONArray array){
        Attachment out = new Attachment();
        for(int i = 0; i<array.length(); i++){
            try {
                JSONObject att = (JSONObject) array.get(i);
                if (att.getString("type").equals("album")){
                    out.type = att.getString("type");
                    out.album = Album.parse(((JSONObject) array.get(i)).getJSONObject("album"));
                }
                if (att.getString("type").equals("photo")){
                    out.type = att.getString("type");
                    out.photo = Photo.parse(((JSONObject) array.get(i)).getJSONObject("photo"));
                }
                if (att.getString("type").equals("link")){
                    out.type = att.getString("type");
                    out.link = Link.parse(((JSONObject) array.get(i)).getJSONObject("link"));
                }
                if (att.getString("type").equals("video")){
                    out.type = att.getString("type");
                    out.video = Video.parse(((JSONObject) array.get(i)).getJSONObject("video"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        Log.d("Attachment", out.album.title);
        return out;
    }
}
