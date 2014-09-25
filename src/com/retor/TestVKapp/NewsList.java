package com.retor.TestVKapp;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import com.retor.TestVKapp.help.Cons;
import com.retor.TestVKapp.help.ListAdapter;
import com.retor.TestVKapp.help.PrefWork;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by retor on 11.09.2014.
 */
public class NewsList extends Activity {

    private String TAG = "Request";
    String url;
    TextView tv;
    ListView lv;
    List<News> newski;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newslist);
        PrefWork prefWork = new PrefWork(getApplicationContext());
        String token = prefWork.loadToken();
        long id = prefWork.loadUserId();
        url = "https://api.vk.com/method/newsfeed.get?user_id=" + id + "&filters=post" + "&count=10" + "&v=" + Cons.API_V + "&access_token=" + token;
        tv = (TextView) findViewById(R.id.textView);
        lv = (ListView) findViewById(R.id.listView);
        newski = new ArrayList<News>();
        asyncTask.execute();

        //adapter = new ListAdapter(getApplicationContext(), R.layout.list_item, newski);
    }

    private JSONObject sendRequest(String url) throws IOException {
        JSONObject object = null;
        URL url2 = new URL(url);
        BufferedReader reader = null;
        Log.d("READER", url);
        try{
            reader = new BufferedReader(new InputStreamReader(url2.openConnection().getInputStream()));
            StringWriter sw = new StringWriter();
            sw.write(reader.readLine());
            Log.d("READER", sw.toString());
            try {
                object = new JSONObject(sw.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        finally{
            if(reader!=null)
                reader.close();
        }
        Log.d("JSON", object.toString());
        return object;
    }

    public ArrayList<News> getNewsArray(JSONObject object) throws JSONException {
        ArrayList<News> out = new ArrayList<News>();
        JSONArray jsonArray = object.getJSONObject("response").getJSONArray("items");
        for (int i = 0; i < jsonArray.length(); ++i){
            News news = new News();
            try {
                news  = News.parse((JSONObject)jsonArray.get(i));
                try {
                    news.setPicture(Drawable.createFromStream((InputStream) new URL(news.getPic()).getContent(), "321"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            out.add(news);
        }
        Log.d("Item text", out.get(0).text);
        return out;
    }

    AsyncTask<Void, Void , Void> asyncTask = new AsyncTask<Void, Void, Void>() {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                newski = getNewsArray(sendRequest(url));

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter = new ListAdapter(getApplicationContext(), newski, R.layout.list_item);
            lv.setAdapter(adapter);
            super.onPostExecute(aVoid);
        }
    };
}
