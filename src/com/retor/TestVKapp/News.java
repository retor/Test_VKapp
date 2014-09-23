package com.retor.TestVKapp;

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
            if (object.has("copy_history")){
                //out.copy_owner_id = object.getJSONArray("copy_history");
                out.text = object.getJSONArray("copy_history").get(6).toString();// getJSONObject("copy_history").getString("text");
            }

        } catch (JSONException e) {
            e.printStackTrace();
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
