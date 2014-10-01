package com.retor.TestVKapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.retor.TestVKapp.classes.News;
import com.retor.TestVKapp.help.Cons;
import com.retor.TestVKapp.help.NewsLoader;
import com.retor.TestVKapp.help.PrefWork;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
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
    String url_next;
    String url_prev;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newslist);
        final PrefWork prefWork = new PrefWork(getApplicationContext());
        String token = prefWork.loadToken();
        long id = prefWork.loadUserId();
        url = "https://api.vk.com/method/newsfeed.get?user_id=" + id + "&filters=post" + "&count=5" + "&v=" + Cons.API_V + "&access_token=" + token;
        lv = (ListView) findViewById(R.id.listView);
        newski = new ArrayList<News>();
        Button logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.webkit.CookieManager.getInstance().removeAllCookie();
                prefWork.clearPref();
                startActivity(new Intent(getApplicationContext(), AuthWeb.class));
                finish();
            }
        });

        asyncTask.execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsFragment newsFragment = new NewsFragment(getApplicationContext(), newski.get(position));
                newsFragment.show(getFragmentManager(), newski.get(position).toString());
            }
        });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private JSONObject sendRequest(String url) throws IOException {
        JSONObject object = null;
        URL request_url = new URL(url);
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new InputStreamReader(request_url.openConnection().getInputStream()));
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

    AsyncTask<Void, Void , Void> asyncTask = new AsyncTask<Void, Void, Void>() {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                newski = new NewsLoader().getNewsArray(sendRequest(url));
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
