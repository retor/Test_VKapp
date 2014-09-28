package com.retor.TestVKapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.retor.TestVKapp.classes.News;

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
        //super(context, resource, objects);
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
        View out;
        ViewHolder holder;
        if (convertView!=null){
            out = convertView;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        out = inflater.inflate(res, parent, false);
        holder = new ViewHolder();
        //set holder views
        holder.text = (TextView)out.findViewById(R.id.item_text);
        holder.author = (TextView)out.findViewById(R.id.item_author);
        holder.date = (TextView)out.findViewById(R.id.item_date);
        holder.picture = (ImageView)out.findViewById(R.id.item_image);
        holder.text.setMaxLines(2);
        holder.comment_pic = (ImageView)out.findViewById(R.id.image_comment);
        holder.like_pic = (ImageView)out.findViewById(R.id.image_like);
        holder.likes = (TextView)out.findViewById(R.id.text_likes);
        holder.comments = (TextView)out.findViewById(R.id.text_comments);
        //fill holder views
        holder.text.setText(getItem(position).getText());
        holder.author.setText(String.valueOf(getItem(position).getPost_id()));
        holder.date.setText(getItem(position).getConv_date());
        holder.picture.setImageDrawable(getItem(position).getPicture());
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
    }
}
