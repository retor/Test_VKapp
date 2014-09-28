package com.retor.TestVKapp.classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Антон on 17.09.2014.
 */
public class Group {
    int id;
    String name;
    String screen_name;
    String type;
    String photo_50;

    public Group(){
    }

    public static Group parse(JSONObject object){
        Group out = new Group();
        try {
            out.id = object.getInt("id");
            out.name = object.getString("name");
            out.screen_name = object.getString("screen_name");
            out.type = object.getString("type");
            out.photo_50 = object.getString("photo_50");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }
}
