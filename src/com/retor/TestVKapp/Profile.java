package com.retor.TestVKapp;

import org.json.JSONObject;

/**
 * Created by Антон on 17.09.2014.
 */
public class Profile {

    int id;
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

        return out;
    }
}
