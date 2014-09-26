package com.retor.TestVKapp.classes;

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

        return out;
    }
}
