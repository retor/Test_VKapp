package com.retor.TestVKapp.help;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Антон on 26.09.2014.
 */
public class PicturesLoader {
    private static PicturesLoader instance = null;
    Context context;

    public PicturesLoader(){}

    protected PicturesLoader(Context context){
        this.context = context;
    }

    public static PicturesLoader instance(Context context){
        if (instance==null){
            instance = new PicturesLoader(context);
        }
        return instance;
    }

    public void loadImage(ImageView imageView, String url){
        Drawable tmp = null;
        Loader loader = new Loader();
        if (url!=null) {
            Executor executor = null;
            executor = Executors.newFixedThreadPool(1);// .newScheduledThreadPool(3);//.awaitTermination(100, TimeUnit.MILLISECONDS);
            try {
                tmp = loader.executeOnExecutor(executor, url).get().getCurrent();
                //tmp = loader.execute(url).get().getCurrent();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Log.d("Loader status", loader.getStatus().name());
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
            Log.d("LoaderPic", "started");
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
