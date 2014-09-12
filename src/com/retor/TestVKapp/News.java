package com.retor.TestVKapp;

import android.graphics.Bitmap;

/**
 * Created by retor on 11.09.2014.
 */
public class News {
    String postName;
    String postText;
    String author;
    int comments;
    int likes;
    Bitmap postPic;

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Bitmap getPostPic() {
        return postPic;
    }

    public void setPostPic(Bitmap postPic) {
        this.postPic = postPic;
    }
}
