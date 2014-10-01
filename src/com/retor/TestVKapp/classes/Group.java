package com.retor.TestVKapp.classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Антон on 17.09.2014.
 */
public class Group {
    public long id;
    public String name;
    public String screen_name;
    public String type;
    public String photo_50;
    public String photo_100;

    public Group(){
    }

    public static Group parse(JSONObject object){
        Group out = new Group();
        try {
            out.id = object.getLong("id");
            out.name = object.getString("name");
            out.screen_name = object.getString("screen_name");
            out.type = object.getString("type");
            out.photo_50 = object.getString("photo_50");
            out.photo_100 = object.getString("photo_100");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }
}
