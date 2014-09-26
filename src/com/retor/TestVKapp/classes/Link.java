package com.retor.TestVKapp.classes;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Антон on 26.09.2014.
 */
public class Link {

    public String url;
    public String title;
    public String description;
    public String img_src;

    public Link(){}

    public static Link parse(JSONObject o){
        Link out_link = new Link();
        try {
            out_link.url = o.getString("url");
            out_link.title = o.getString("title");
            out_link.description = o.getString("description");
            out_link.img_src = o.getString("image_src");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return out_link;
    }
}
