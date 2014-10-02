package com.retor.TestVKapp.help;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
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
    int cacheSize = 4 * 1024 * 1024;
    LruCache picturesCahche;

    protected PicturesLoader(Context context){
        this.context = context;
        picturesCahche = new LruCache(cacheSize){
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }};
    }

    public static PicturesLoader instance(Context context){
        if (instance==null){
            instance = new PicturesLoader(context);
        }
        return instance;
    }

    public void loadImage(ImageView imageView, String url){
        Bitmap tmp = null;
        Loader loader = new Loader();
        if (url!=null) {
            Executor executor = Executors.newFixedThreadPool(1);
            try {
                tmp = loader.executeOnExecutor(executor, url).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Log.d("Loader status", loader.getStatus().name()+ " " +url);
        }
        String key = url.toString();;

        imageView.setImageBitmap(tmp);
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            picturesCahche.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return (Bitmap)picturesCahche.get(key);
    }

  /*  public void loadBitmap(String resId, ImageView imageView) {
        final String imageKey = resId;

        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        } else {
            mImageView.setImageResource(R.drawable.image_placeholder);
            BitmapWorkerTask task = new BitmapWorkerTask(mImageView);
            task.execute(resId);
        }
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            final Bitmap bitmap = BitmapFactory.decode decodeSampledBitmapFromResource(
                    getResources(), params[0], 100, 100));
            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            return bitmap;
        }

    }*/

    private class Loader extends AsyncTask<String, Bitmap, Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Log.d("LoaderPic", "started");
            Bitmap bit_out = null;
            HttpGet httpRequest = null;
            try {
                httpRequest = new HttpGet(new URL(params[0]).toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = null;
            InputStream instream=null;
            try {
                response = (HttpResponse) httpclient.execute(httpRequest);
                HttpEntity entity = response.getEntity();
                BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
                instream = bufHttpEntity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bit_out = BitmapFactory.decodeStream(instream);
            addBitmapToMemoryCache(params[0],bit_out);
            return bit_out;
        }

        @Override
        protected void onPostExecute(Bitmap aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
