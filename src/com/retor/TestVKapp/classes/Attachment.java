package com.retor.TestVKapp.classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Антон on 26.09.2014.
 */
public class Attachment {
    String type;
    public Album album;

    public Attachment(){}

    public static Attachment parse(JSONArray array){
        Attachment out = new Attachment();
        for(int i = 0; i<array.length(); i++){
            try {
                JSONObject att = (JSONObject) array.get(i);
                if (att.getString("type").equals("album")){
                    out.album = Album.parse(((JSONObject) array.get(i)).getJSONObject("album"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        Log.d("Attachment", out.album.title);
        return out;
    }
}
