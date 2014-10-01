package com.retor.TestVKapp;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.retor.TestVKapp.classes.News;
import com.retor.TestVKapp.classes.Photo;
import com.retor.TestVKapp.help.PicturesLoader;

import java.util.ArrayList;

/**
 * Created by Антон on 29.09.2014.
 */
public class NewsFragment extends DialogFragment {
    private Context context;
    private News news;

    public NewsFragment(Context context, News news) {
        super();
        setArguments(new Bundle());
        this.context = context;
        this.news = news;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Fragment created", "started");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View out_view = inflater.inflate(R.layout.newsfragment, container, false);
        LinearLayout ll = (LinearLayout)out_view.findViewById(R.id.ll);
        ViewGroup.LayoutParams linLayoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        PicturesLoader loader = PicturesLoader.instance(getActivity());
        //Initial views
        LinearLayout layoutPerson = new LinearLayout(getActivity());
        layoutPerson.setOrientation(LinearLayout.HORIZONTAL);
        layoutPerson.setLayoutParams(linLayoutParam);
        ImageView personImage = new ImageView(getActivity());
        personImage.setMinimumHeight(50);
        personImage.setMinimumWidth(50);
        TextView personName = new TextView(getActivity());
        if (news.profile!=null){
            loader.loadImage(personImage, news.profile.photo_50);
            personName.setText(news.profile.first_name + " " + news.profile.last_name);
        }else{
            loader.loadImage(personImage, news.group.photo_50);
            personName.setText(news.group.name);
        }
        layoutPerson.addView(personImage);
        layoutPerson.addView(personName, linLayoutParam);
        ll.addView(layoutPerson);


        if (news.text!=null || news.copy_text!=null){
            TextView textView = new TextView(getActivity());
            if (news.text!=null){
                textView.setText(news.text);
            }else{
                if (news.copy_text!=null)
                    textView.setText(news.copy_text);
            }
            if (textView.getText()!=null)
                ll.addView(textView);
        }

        if (news.attachment.type.equals("album")){

            for (int i=0; i<news.attachment.album.size; i++){
                ImageView imageView = new ImageView(getActivity());
                loader.loadImage(imageView, news.attachment.album.thumb.photo_130);
                ll.addView(imageView, linLayoutParam);
            }
        }
        setCancelable(true);
        out_view.canScrollVertically(50);
        return out_view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private ArrayList<Photo> requestAlbum(News news_in){
        ArrayList<Photo> out = new ArrayList<Photo>();

        return out;
    }
}
