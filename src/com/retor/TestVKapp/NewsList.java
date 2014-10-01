package com.retor.TestVKapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.retor.TestVKapp.classes.News;
import com.retor.TestVKapp.help.NewsLoader;
import com.retor.TestVKapp.help.PrefWork;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by retor on 11.09.2014.
 */
public class NewsList extends Activity {

    private String TAG = "Request";
    ListView lv;
    List<News> newski;
    ListAdapter adapter;
    NewsLoader newsLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newslist);
        newsLoader = NewsLoader.instance(getApplicationContext());


        lv = (ListView) findViewById(R.id.listView);
        newski = new ArrayList<News>();
        Button logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.webkit.CookieManager.getInstance().removeAllCookie();
                new PrefWork(getApplicationContext()).clearPref();
                startActivity(new Intent(getApplicationContext(), AuthWeb.class));
                finish();
            }
        });

        //asyncTask.execute();
        new TaskRequest().execute();
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
                if ((totalItemCount-3)==firstVisibleItem){
                    new TaskRequest().execute();
                }
            }
        });
    }



    AsyncTask<Void, Void , Void> asyncTask = new AsyncTask<Void, Void, Void>() {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (newski==null) {
                try {
                    newski = newsLoader.getNewsArray();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    newski.addAll(newsLoader.getNewsArray());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (adapter==null){
                adapter = new ListAdapter(getApplicationContext(), newski, R.layout.list_item);
                lv.setAdapter(adapter);
            }else {
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();
            }
            super.onPostExecute(aVoid);
        }
    };

    private class TaskRequest extends AsyncTask<Void, Void , Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (newski.size()==0) {
                try {
                    newski = newsLoader.getNewsArray();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    newski.addAll(newsLoader.getNewsArray());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (adapter==null){
                adapter = new ListAdapter(getApplicationContext(), newski, R.layout.list_item);
                lv.setAdapter(adapter);
            }else {
                adapter.notifyDataSetChanged();
                int i = lv.getLastVisiblePosition();
                lv.setAdapter(adapter);
                lv.deferNotifyDataSetChanged();
                lv.setSelection(i);
                //adapter.notifyDataSetInvalidated();

            }
            super.onPostExecute(aVoid);
        }
    }
}
