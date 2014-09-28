package com.retor.TestVKapp.classes;

import android.graphics.drawable.Drawable;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * Created by Антон on 17.09.2014.
 */
public class News {

    public String type;
    public int source_id;
    public long date;
    public String conv_date;
    public int post_id;
    public String text;
    public int comments_count;
    public int likes_count;
    public int copy_owner_id;
    public String copy_text;
    public String pic;
    public Drawable picture;
    public Attachment attachment;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    Profile profile;
    Group group;

    public News(){
    }

    public News parse(JSONObject object){
        News out = new News();
        try {
            out.setType(object.getString("type"));
            out.setSource_id(object.getInt("source_id"));
            out.setDate(object.getLong("date"));
            out.setPost_id(object.getInt("post_id"));
            out.setText(object.getString("text"));
            out.setComments_count(object.getJSONObject("comments").getInt("count"));
            out.setLikes_count(object.getJSONObject("likes").getInt("count"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray copy_history_json=object.optJSONArray("copy_history");
        if(copy_history_json!=null) {
            Log.i("NewsItem", copy_history_json.toString());
            for (int i = 0; i < copy_history_json.length(); i++) {
                try {
                    out.setDate(copy_history_json.getJSONObject(i).getLong("date"));
                    out.setText(copy_history_json.getJSONObject(i).getString("text"));
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }
        JSONArray attach_json=object.optJSONArray("attachments");
        if(attach_json!=null) {
            attachment = new Attachment();
            out.attachment = Attachment.parse(attach_json);
        }
        return out;
    }

    public Drawable getPicture() {
        return picture;
    }

    public void setPicture(Drawable picture) {
        this.picture = picture;
    }

    public Drawable loadPicture(String url){
        Drawable out = null;
        try {
            out = Drawable.createFromStream((InputStream) new URL(url).getContent(), "321");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getConv_date() {
        conv_date = new Date(date * 1000).toString();
        return conv_date;
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
}
