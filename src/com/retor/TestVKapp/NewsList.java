package com.retor.TestVKapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.retor.TestVKapp.help.Cons;
import com.retor.TestVKapp.help.PrefWork;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by retor on 11.09.2014.
 */
public class NewsList extends Activity {

    private String TAG = "Request";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newslist);
        TextView tv = (TextView) findViewById(R.id.textView);
        PrefWork prefWork = new PrefWork(getApplicationContext());
        String token = prefWork.loadToken();
        long id = prefWork.loadUserId();
        tv.setText(token + " " + id);
        Button refresh = (Button)findViewById(R.id.refresh);


        url = "https://api.vk.com/method/newsfeed.get?user_id=" + id + "&count=1" + "&v=" + Cons.API_V + "&access_token=" + token;
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    parseJson(sendRequestInternal(url));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                thread.run();
            }
        });
    }

    private JSONObject sendRequestInternal(String url) throws IOException {
        JSONObject object = null;
        URL url2 = new URL(url);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream()));
        StringWriter sw = new StringWriter();
        sw.write(reader.readLine());
        Log.d("READER", sw.toString());
        try {
            String response = sw.toString();
            object = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("JSON", object.toString());
        return object;
    }

    public ArrayList<News> parseJson(JSONObject o) throws JSONException {
        ArrayList<News> newsArray = new ArrayList<News>();
        JSONObject response = o.optJSONObject("response");
        JSONArray array = response.optJSONArray("items");
        for (int i = 0; i < array.length(); ++i) {
            newsArray.add(new News((JSONObject)array.get(i)));
            //JSONObject rec = news.getJSONObject(i);
//            JSONObject jsonPage = rec.getJSONObject("page");
//            String address = jsonPage.getString("url");
//            String name = jsonPage.getString("name");
//            String status = jsonPage.getString("status");
            Log.d("Parsed",newsArray.get(i).getPostText());
        }
        return newsArray;
    }
            /*
            HttpURLConnection connection=null;
           connection = (HttpURLConnection)new URL(url).openConnection();
           connection.setConnectTimeout(30000);
           connection.setReadTimeout(30000);
           connection.setUseCaches(false);
           connection.setDoOutput(is_post);
           connection.setDoInput(true);
           connection.setRequestMethod(is_post?"POST":"GET");
*//*           if(is_post)
               connection.getOutputStream().write(body.getBytes("UTF-8"));*//*
//           int code=connection.getResponseCode();
           //Log.i(TAG, "code=" + code);
           //It may happen due to keep-alive problem http://stackoverflow.com/questions/1440957/httpurlconnection-getresponsecode-returns-1-on-second-invocation

            InputStream is = new BufferedInputStream(connection.getInputStream(), 8192);
*//*           String enc=connection.getHeaderField("Content-Encoding");
           if(enc!=null && enc.equalsIgnoreCase("gzip"))
              is = new GZIPInputStream(is);*//*
            InputStreamReader r = new InputStreamReader(is);
            StringWriter sw = new StringWriter();
                     char[] buffer = new char[1024];
                     try {
                             for (int n; (n = r.read(buffer)) != -1;)
                                     sw.write(buffer, 0, n);
                         }catch (IOException e1) {
                              e1.printStackTrace();
                         }
            String response=sw.toString();
            Log.d(TAG, response);
            try {
                JSONObject object = new JSONObject(response);
                Log.d("JSON", object.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }
            finally{
               if(connection!=null)
            connection.disconnect();
            }*/
//    }


}
