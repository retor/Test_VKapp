package com.retor.TestVKapp.classes;

import android.graphics.drawable.Drawable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Антон on 17.09.2014.
 */
public class Group {
    public long id;
    String name;
    String screen_name;
    String type;
    String photo_50;
    public Drawable picture;

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto_50() {
        return photo_50;
    }
}
