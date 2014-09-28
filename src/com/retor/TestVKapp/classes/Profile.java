package com.retor.TestVKapp.classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Антон on 17.09.2014.
 */
public class Profile {

    public int id;
    String first_name;
    String last_name;
    String sex;
    String screen_name;
    String photo_50;
    Boolean online;

    public Profile(){
    }

    public static Profile parse(JSONObject object){
        Profile out = new Profile();
        try {
            out.id = object.getInt("id");
            out.first_name = object.getString("first_name");
            out.last_name = object.getString("last_name");
            out.sex = getGender(object.getInt("sex"));
            out.screen_name = object.getString("screen_name");
            out.photo_50 = object.getString("photo_50");
            out.online = checkOnline(object.getInt("online"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out;
    }

    private static String getGender(int sex){
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

    private static boolean checkOnline(int in){
        boolean out = false;
           if (in == 1)
               out = true;
        return out;
    }
}
