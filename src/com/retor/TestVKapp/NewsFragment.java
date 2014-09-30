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
import com.retor.TestVKapp.classes.News;
import com.retor.TestVKapp.help.PicturesLoader;

import java.util.ArrayList;

/**
 * Created by Антон on 29.09.2014.
 */
public class NewsFragment extends DialogFragment {
    Context context;
    private News news;

    public NewsFragment(Context context, News news) {
        super();
        this.context = context;
        this.news = news;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Fragment created", "started");
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(android.os.Bundle)} and {@link #onActivityCreated(android.os.Bundle)}.
     * <p/>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //View out_view = inflater.inflate(R.layout.newsfragment, container, false);
        View v = new View(context);
        LinearLayout out_view = new LinearLayout(getDialog().getContext());
        out_view.setOrientation(LinearLayout.VERTICAL);
        // создаем LayoutParams
        ViewGroup.LayoutParams linLayoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        out_view.setLayoutParams(new ViewGroup.LayoutParams(350, 350));
        ArrayList<View> views = new ArrayList<View>();
        if (news.attachment.type.equals("album")){
            PicturesLoader loader = PicturesLoader.instance(getDialog().getContext());
            for (int i=0; i<news.attachment.album.size; i++){
                ImageView imageView = new ImageView(this.getDialog().getContext());
                imageView.setMaxHeight(150);
                imageView.setMaxWidth(150);
                loader.loadImage(imageView, news.attachment.album.thumb.photo_130);
                views.add(imageView);

            }
            out_view.addChildrenForAccessibility(views);
        }
        
        //init views

        //fill views

        return out_view;
    }

    /**
     * Called immediately after {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
