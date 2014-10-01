package com.retor.TestVKapp.classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Антон on 17.09.2014.
 */
public class Profile {

    public long id;
    public String first_name;
    public String last_name;
    private String sex;
    public String screen_name;
    public String photo_50;
    public String photo_100;
    private  Boolean online;

    public Profile(){
    }

    public static Profile parse(JSONObject object){
        Profile out = new Profile();
        try {
            out.id = object.getLong("id");
            out.first_name = object.getString("first_name");
            out.last_name = object.getString("last_name");
            out.sex = getGender(object.getInt("sex"));
            out.screen_name = object.getString("screen_name");
            out.photo_50 = object.getString("photo_50");
            out.photo_100 = object.getString("photo_100");
            out.online = checkOnline(object.getInt("online"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }

    public static String getGender(int sex){
        String out = null;
        if (sex == 2){
            out = "male";
        }
        if (sex == 1){
            out = "female";
        }
        if (out!=null)
        return out;
        else
        return "unknown";
    }

    public static boolean checkOnline(int in){
        boolean out = false;
           if (in == 1)
               out = true;
        return out;
    }
}
