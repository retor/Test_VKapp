package com.retor.TestVKapp.help;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Антон on 26.09.2014.
 */
public class PicturesLoader {

    Context context;

    public PicturesLoader(){}

    public PicturesLoader(Context context){
        this.context = context;
    }

    public void loadImage(ImageView imageView, String url){
        Loader loader = new Loader();
        Drawable tmp = null;
        try {
            tmp = loader.execute(url).get().getCurrent();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        imageView.setImageDrawable(tmp);
    }

    private class Loader extends AsyncTask<String, Drawable, Drawable>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Drawable doInBackground(String... params) {
            //Drawable picture
            Drawable picture = null;
            for (String str : params) {
                try {
                    InputStream inputStream = (InputStream) new URL(str).getContent();
                    picture = Drawable.createFromStream(inputStream, "321");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return picture;
        }

        @Override
        protected void onPostExecute(Drawable aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
