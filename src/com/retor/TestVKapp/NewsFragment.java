package com.retor.TestVKapp;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.retor.TestVKapp.classes.News;
import com.retor.TestVKapp.help.PicturesLoader;

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
        //Initial profile views
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginStart(20);
        LinearLayout layoutPerson = new LinearLayout(getActivity());
        layoutPerson.setOrientation(LinearLayout.HORIZONTAL);
        layoutPerson.setLayoutParams(linLayoutParam);
        ImageView personImage = new ImageView(getActivity());
        TextView personName = new TextView(getActivity());
        TextView time = new TextView(getActivity());
        personImage.setMinimumHeight(70);
        personImage.setMinimumWidth(70);
        personName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        personName.setLayoutParams(params);
        time.setText(news.getConv_date().substring(0, 19));
        time.setLayoutParams(params);
        if (news.profile!=null){
            loader.loadImage(personImage, news.profile.photo_100);
            personName.setText(news.profile.first_name + " " + news.profile.last_name);
        }else{
            loader.loadImage(personImage, news.group.photo_100);
            personName.setText(news.group.name);
        }
        layoutPerson.addView(personImage);
        layoutPerson.addView(personName);
        layoutPerson.addView(time);
        ll.addView(layoutPerson);

        //init next LinerLayout for message
        LinearLayout layoutMessage = new LinearLayout(getActivity());
        layoutPerson.setOrientation(LinearLayout.HORIZONTAL);
        TextView messageText = new TextView(getActivity());
        ImageView messagePic = new ImageView(getActivity());
        messagePic.setMinimumHeight(230);
        messagePic.setMinimumWidth(230);
        messageText.setLayoutParams(params);
        if (news.attachment.type.equals("photo")){
            loader.loadImage(messagePic, news.attachment.photo.photo_604);
            layoutMessage.addView(messagePic);
        }
        if (news.attachment.type.equals("video")){
            loader.loadImage(messagePic, news.attachment.video.photo_640);
            layoutMessage.addView(messagePic);
        }
        if (news.text!=null || news.copy_text!=null){
            if (news.text!=null){
                messageText.setText(news.text);
            }else{
                if (news.copy_text!=null)
                    messageText.setText(news.copy_text);
            }
            if (messageText.getText()!=null)
                layoutMessage.addView(messageText);
        }
        ll.addView(layoutMessage);
        setCancelable(true);
        out_view.canScrollVertically(50);
        return out_view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
