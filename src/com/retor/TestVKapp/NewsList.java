package com.retor.TestVKapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.retor.TestVKapp.classes.News;
import com.retor.TestVKapp.help.NewsLoader;
import com.retor.TestVKapp.help.PicturesLoader;
import com.retor.TestVKapp.help.PrefWork;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by retor on 11.09.2014.
 */
public class NewsList extends Activity {

    private String TAG = "Request";
    private ListView lv;
    private ArrayList<News> newski;
    private ListAdapter adapter;
    private NewsLoader newsLoader;
    private TaskRequest task;
    private boolean stopScroll = true;
    private PicturesLoader picloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newslist);
        newsLoader = NewsLoader.instance(getApplicationContext(), this);
        picloader = PicturesLoader.instance(getApplicationContext());
        newski = new ArrayList<News>();
        initViews();
        task = new TaskRequest();
        task.execute(0);
    }

    private void initViews(){
        lv = (ListView) findViewById(R.id.listView);
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
                if (visibleItemCount > 0 && firstVisibleItem + visibleItemCount == totalItemCount-1) {
                    if (newsLoader.isHAS_NEXT() && !stopScroll){
                        stopScroll=true;
                        new TaskRequest().execute(1);
                       adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        Button refresh = (Button)findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        Button logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.webkit.CookieManager.getInstance().removeAllCookie();
                new PrefWork(getApplicationContext()).clearPref();
                newsLoader.clearLoader();
                startActivity(new Intent(getApplicationContext(), AuthWeb.class));
                finish();
            }
        });
    }

    private void refresh(){
        newski = new ArrayList<News>();
        task = new TaskRequest();
        task.execute(0);
        adapter.array.clear();
        adapter.array.addAll(newski);
        adapter.notifyDataSetChanged();
        lv.invalidateViews();
    }

    private class TaskRequest extends AsyncTask<Integer, Integer, Integer>{

        @Override
        protected Integer doInBackground(Integer... params) {
            ArrayList<News> tmp=null;
            if (params[0]==0) {
                try {
                    tmp = newsLoader.getNewsArray(params[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newski = tmp;
            }
            if (params[0]==1){
                try {
                    tmp = newsLoader.getNewsArray(params[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (tmp!=null)
                    newski.addAll(tmp);
                adapter.array.addAll(tmp);
            }
            if(newski!=null && newski.size()!=0) {
                for (int i = 0; i < newski.size(); i++) {
                    if (newski.get(i).profile != null) {
                        picloader.fillCache(newski.get(i).getProfile().photo_50);
                    } else {
                        picloader.fillCache(newski.get(i).getGroup().photo_50);
                    }
                    if (newski.get(i).attachment != null && newski.get(i).attachment.type.equals("album")) {
                        picloader.fillCache(newski.get(i).attachment.album.thumb.photo_75);
                    }
                    if (newski.get(i).attachment != null && newski.get(i).attachment.type.equals("photo")) {
                        picloader.fillCache(newski.get(i).attachment.photo.photo_75);
                    }
                    if (newski.get(i).attachment != null && newski.get(i).attachment.type.equals("video")) {
                        picloader.fillCache(newski.get(i).attachment.video.photo_130);
                    }
                }
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(Integer aVoid) {
            if (newski!=null && newski.size()!=0) {
                if (aVoid == 0) {
                    adapter = new ListAdapter(getApplicationContext(), newski, R.layout.list_item);
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    stopScroll = false;
                }
                if (aVoid == 1) {
                    adapter.notifyDataSetChanged();
                    stopScroll = false;
                }
            }
            super.onPostExecute(aVoid);
        }
    }
}
