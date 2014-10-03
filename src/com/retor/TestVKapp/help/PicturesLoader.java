package com.retor.TestVKapp.help;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import com.retor.TestVKapp.R;
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

/**
 * Created by Антон on 26.09.2014.
 */
public class PicturesLoader {
    private static PicturesLoader instance = null;
    Context context;
    int cacheSize = 8 * 1024 * 1024;
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
        final String imageKey = url;
        Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.no_picture);
            BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            task.execute(url);
            Log.d("Loader status", task.getStatus().name()+ " " +url);
        }
    }

    public void fillCache(String url){
        ChacheFiller filler = new ChacheFiller();
        Bitmap bitmap = getBitmapFromMemCache(url);
        if (bitmap==null)
        filler.execute(url);
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            picturesCahche.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return (Bitmap)picturesCahche.get(key);
    }

    class ChacheFiller extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
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
            addBitmapToMemoryCache(params[0], bit_out);
            return bit_out;
        }
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        ImageView mImageView;
        BitmapWorkerTask(ImageView imageView) {
            mImageView = imageView;
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
            addBitmapToMemoryCache(params[0], bit_out);
            return bit_out;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mImageView.setImageBitmap(bitmap);
        }
    }
}
