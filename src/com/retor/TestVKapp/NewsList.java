package com.retor.TestVKapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.retor.TestVKapp.help.Cons;
import com.retor.TestVKapp.help.PrefWork;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

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
        url = "https://api.vk.com/method/newsfeed.get?user_id=" + id + "&count=1" + "&v=" + Cons.API_V + "&access_token=" + token;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendRequestInternal(url);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private JSONObject sendRequestInternal(String url) throws IOException {
        JSONObject object = null;
        URL url2 = new URL(url);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url2.openStream()));
        Log.d("READER", reader.readLine());
        try {
            object = new JSONObject(reader.readLine().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("JSON", object.toString());
        return object;
    }

    public void parseJson(JSONObject o) throws JSONException {
        JSONArray news = new JSONArray(o);
        for (int i = 0; i < news.length(); ++i) {
            JSONObject rec = news.getJSONObject(i);
            JSONObject jsonPage = rec.getJSONObject("page");
            String address = jsonPage.getString("url");
            String name = jsonPage.getString("name");
            String status = jsonPage.getString("status");
        }
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
