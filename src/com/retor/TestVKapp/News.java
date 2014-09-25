package com.retor.TestVKapp;

import android.graphics.drawable.Drawable;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Антон on 17.09.2014.
 */
public class News {

    String type;
    int source_id;
    long date;
    int post_id;
    String text;
    int comments_count;
    int likes_count;
    int copy_owner_id;
    String copy_text;
    String pic;
    Drawable picture;

    public Drawable getPicture() {
        return picture;
    }

    public void setPicture(Drawable picture) {
        this.picture = picture;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    ArrayList<Profile> profiles;
    ArrayList<Group> groups;

    public News(){
    }

    public static News parse(JSONObject object){
        News out = new News();
        try {
            out.type = object.getString("type");
            out.source_id = object.getInt("source_id");
            out.date = object.getLong("date");
            out.post_id = object.getInt("post_id");
            out.text = object.getString("text");
            out.comments_count = object.getJSONObject("comments").getInt("count");
            out.likes_count = object.getJSONObject("likes").getInt("count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray copy_history_json=object.optJSONArray("copy_history");
            if(copy_history_json!=null) {
                Log.i("NewsItem", copy_history_json.toString());
                for (int i = 0; i < copy_history_json.length(); ++i) {
                    try {
                        out.date = copy_history_json.getJSONObject(i).getLong("date");
                        out.text = copy_history_json.getJSONObject(i).getString("text");
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            }
        JSONArray attach_json=object.optJSONArray("attachments");
        if(attach_json!=null) {
            for (int i = 0; i < attach_json.length(); ++i) {
                try {
                    //if (attach_json.getJSONObject(i).getString("type").equalsIgnoreCase("photo"))
                    out.pic = attach_json.getJSONObject(i).getJSONObject("photo").getString("photo_75");
                    Log.d("Object picture", attach_json.getJSONObject(i).getJSONObject("photo").getString("photo_75").toString());
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }

        return out;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSource_id() {
        return source_id;
    }

    public void setSource_id(int source_id) {
        this.source_id = source_id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getCopy_owner_id() {
        return copy_owner_id;
    }

    public void setCopy_owner_id(int copy_owner_id) {
        this.copy_owner_id = copy_owner_id;
    }

    public String getCopy_text() {
        return copy_text;
    }

    public void setCopy_text(String copy_text) {
        this.copy_text = copy_text;
    }

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }
}
