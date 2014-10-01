package com.retor.TestVKapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.retor.TestVKapp.classes.News;
import com.retor.TestVKapp.help.PicturesLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Антон on 17.09.2014.
 */
public class ListAdapter extends BaseAdapter {

    List<News> array;
    Context context;
    int res;

    public ListAdapter(Context context, int resource) {
        this.context = context;
        this.res = resource;
    }

    public ListAdapter(Context context, List<News> objects, int resource) {
        array = new ArrayList<News>(objects);
        this.context = context;
        res = resource;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public News getItem(int position) {
        return array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View out = convertView;
        ViewHolder holder = new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        PicturesLoader loader = PicturesLoader.instance(context);
        if (out==null)
            out = inflater.inflate(res, parent, false);
        //set holder views
        holder.text = (TextView)out.findViewById(R.id.item_text);
        holder.author = (TextView)out.findViewById(R.id.item_author);
        holder.date = (TextView)out.findViewById(R.id.item_date);
        holder.picture = (ImageView)out.findViewById(R.id.item_image);
        holder.text.setMaxLines(5);
        holder.comment_pic = (ImageView)out.findViewById(R.id.image_comment);
        holder.like_pic = (ImageView)out.findViewById(R.id.image_like);
        holder.likes = (TextView)out.findViewById(R.id.text_likes);
        holder.comments = (TextView)out.findViewById(R.id.text_comments);
        holder.author_pic = (ImageView)out.findViewById(R.id.author_pic);
        //fill holder views
        holder.text.setText(getItem(position).getText());
        if (getItem(position).profile!=null){
            loader.loadImage(holder.author_pic, getItem(position).getProfile().photo_50);
            holder.author.setText(getItem(position).profile.first_name + " " + getItem(position).profile.last_name);
        }else{
            loader.loadImage(holder.author_pic, getItem(position).getGroup().photo_50);
            holder.author.setText(getItem(position).group.name);
        }
        holder.date.setText(getItem(position).getConv_date());
        if (getItem(position).attachment!=null && getItem(position).attachment.type.equals("album")) {
            loader.loadImage(holder.picture, getItem(position).attachment.album.thumb.photo_75);
        }
        if (getItem(position).attachment!=null && getItem(position).attachment.type.equals("photo")) {
            loader.loadImage(holder.picture, getItem(position).attachment.photo.photo_75);
        }
        if (getItem(position).attachment!=null && getItem(position).attachment.type.equals("video")) {
            loader.loadImage(holder.picture, getItem(position).attachment.video.photo_130);
        }
        holder.comments.setText(String.valueOf(getItem(position).getComments_count()));
        holder.likes.setText(String.valueOf(getItem(position).getLikes_count()));
        return out;
    }

    private class ViewHolder{
        TextView text;
        TextView author;
        TextView date;
        ImageView picture;
        ImageView like_pic;
        ImageView comment_pic;
        TextView likes;
        TextView comments;
        ImageView author_pic;
    }
}
