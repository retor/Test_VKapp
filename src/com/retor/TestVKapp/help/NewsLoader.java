package com.retor.TestVKapp.help;

import android.content.Context;
import android.util.Log;
import com.retor.TestVKapp.classes.Group;
import com.retor.TestVKapp.classes.News;
import com.retor.TestVKapp.classes.Profile;
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
 * Created by Антон on 29.09.2014.
 */
public class NewsLoader {

    private static NewsLoader instance = null;
    private Context context;
    private String start_from;
    private long id;
    private String token;
    private String url_request;// = "https://api.vk.com/method/newsfeed.get?user_id=" + id + "&filters=post" + "&count=5" + "&v=" + Cons.API_V + "&access_token=" + token;
//    private String url_new_request = "https://api.vk.com/method/newsfeed.get?user_id=" + id + "&filters=post" + "&count=5" + "&start_from=" + start_from + "&v=" + Cons.API_V + "&access_token=" + token;

    protected NewsLoader(Context in){
        context = in;
        PrefWork prefWork = new PrefWork(context);
        token = prefWork.loadToken();
        id = prefWork.loadUserId();
        url_request = "https://api.vk.com/method/newsfeed.get?user_id=" + id + "&filters=post" + "&count=5" + "&v=" + Cons.API_V + "&access_token=" + token;
    }

    public static NewsLoader instance(Context context){
        if (instance==null){
            instance = new NewsLoader(context);
        }
        return instance;
    }

    private String createNewUrl(long id, String token, String start_from){
        String out;
        if (start_from==null){
            out = "https://api.vk.com/method/newsfeed.get?user_id=" + id + "&filters=post" + "&count=5" + "&v=" + Cons.API_V + "&access_token=" + token;
        }else{
            out = "https://api.vk.com/method/newsfeed.get?user_id=" + id + "&filters=post" + "&start_from=" + start_from + "&count=5" + "&v=" + Cons.API_V + "&access_token=" + token;
        }
        return out;
    }

    private JSONObject sendRequest(String url) throws IOException {
        Log.d("URL", url);
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
        try {
            String tmp_start = object.getJSONObject("response").getString("next_from");
            if (tmp_start!=null)
            start_from = tmp_start;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public ArrayList<News> getNewsArray() throws JSONException {
        JSONObject object = null;
        try {
            object = sendRequest(createNewUrl(id, token, start_from));
        } catch (IOException e) {
                e.printStackTrace();
        }
        ArrayList<News> out = new ArrayList<News>();
        if (object!=null) {
            JSONArray jsonArray = object.getJSONObject("response").getJSONArray("items");
            ArrayList<Profile> profiles = getProfArray(object.getJSONObject("response").getJSONArray("profiles"));
            ArrayList<Group> groups = getGroupArray(object.getJSONObject("response").getJSONArray("groups"));
            for (int i = 0; i < jsonArray.length(); i++) {
                News news = new News();
                news = news.parse((JSONObject) jsonArray.get(i));
                if (news.source_id > 0) {
                    news.setProfile(getProf(profiles, news.getSource_id()));
                } else {
                    news.setGroup(getGroup(groups, news.getSource_id()));
                }
                if (news.copy_owner_id != 0) {
                    if (news.copy_owner_id > 0) {
                        news.setProfile(getProf(profiles, news.copy_owner_id));
                    } else {
                        news.setGroup(getGroup(groups, news.copy_owner_id));
                    }
                } else {
                    if (news.signer_id != 0 && news.signer_id > 0) {
                        news.setProfile(getProf(profiles, news.signer_id));
                    }
                }
                out.add(news);
            }
        }
        return out;
    }

    private Profile getProf(ArrayList<Profile> arrayin, long in){
        Profile profile = new Profile();
        for (int i=0; i< arrayin.size(); i++){
            if (arrayin.get(i).id == in)
                profile = arrayin.get(i);
        }
        return profile;
    }

    private Group getGroup(ArrayList<Group> arrayin, long in){
        Group group = new Group();
        for (int i=0; i< arrayin.size(); i++){
            if (((arrayin.get(i).id)*-1) == in || arrayin.get(i).id == in)
                group = arrayin.get(i);
        }
        return group;
    }

    private ArrayList<Profile> getProfArray(JSONArray in){
        ArrayList<Profile> out = new ArrayList<Profile>();
        if (in!=null)
            for (int i=0; i<in.length(); i++){
                Profile profile = new Profile();
                try {
                    profile = profile.parse((JSONObject)in.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                out.add(profile);
            }
        return out;
    }

    private ArrayList<Group> getGroupArray(JSONArray in){
        ArrayList<Group> out = new ArrayList<Group>();
        if (in!=null)
            for (int i=0; i<in.length(); i++){
                Group group = new Group();
                try {
                    group = group.parse((JSONObject)in.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                out.add(group);
            }
        return out;
    }
}
