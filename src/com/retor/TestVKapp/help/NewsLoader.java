package com.retor.TestVKapp.help;

import android.app.Activity;
import android.content.*;
import android.util.Log;
import android.widget.Toast;
import com.retor.TestVKapp.AuthWeb;
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
public class NewsLoader{

    private static NewsLoader instance = null;
    private Context context;
    private String start_from;
    private long id;
    private String token;
    private Activity parent;

    final static int MODE_NEW_REFRESH=0;
    final static int MODE_NEXT_LOAD=1;


    protected NewsLoader(Context in, Activity activity){
        context = in;
        PrefWork prefWork = new PrefWork(context);
        token = prefWork.loadToken();
        id = prefWork.loadUserId();
        parent = activity;
        //url_request = "https://api.vk.com/method/newsfeed.get?user_id=" + id + "&filters=post" + "&count=5" + "&v=" + Cons.API_V + "&access_token=" + token;
    }

    public static NewsLoader instance(Context context, Activity activity){
        if (instance==null){
            instance = new NewsLoader(context, activity);
        }
        return instance;
    }

    private String createNewUrl(int mode){
        String out=null;
        if (mode==MODE_NEW_REFRESH){
            out = "https://api.vk.com/method/newsfeed.get?user_id=" + id + "&filters=post" + "&count=5" + "&v=" + Cons.API_V + "&access_token=" + token;
        }
        if (mode==MODE_NEXT_LOAD) {
            if (isHAS_NEXT()){
                out = "https://api.vk.com/method/newsfeed.get?user_id=" + id + "&filters=post" + "&start_from=" + start_from + "&count=5" + "&v=" + Cons.API_V + "&access_token=" + token;
            }
        }
        return out;
    }

    public boolean isHAS_NEXT(){
        boolean out = false;
        if (!start_from.isEmpty()) {
            out = true;
        }
        return out;
    }

    private JSONObject sendRequest(String url) throws IOException {
        JSONObject object = null;
        if (url!=null) {
            Log.d("URL", url);
            URL request_url = new URL(url);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(request_url.openConnection().getInputStream()));
                StringWriter sw = new StringWriter();
                sw.write(reader.readLine());
                Log.d("READER", sw.toString());
                try {
                    object = new JSONObject(sw.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } finally {
                if (reader != null)
                    reader.close();
            }
            Log.d("JSON", object.toString());
            if (object.optJSONObject("response")!=null) {
                String tmp_start = object.optJSONObject("response").optString("next_from");
                if (tmp_start != null) {
                    start_from = tmp_start;
                }
            }
        }
        return object;
    }

    public ArrayList<News> getNewsArray(int mode) throws JSONException {
        JSONObject object=null;
        String url=createNewUrl(mode);
            try {
                object = sendRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        ArrayList<News> out = new ArrayList<News>();
        if (object!=null) {
            if (object.isNull("response") && !object.isNull("error")){
                final JSONObject finalObject1 = object;
                parent.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(parent.getApplicationContext(), finalObject1.getJSONObject("error").getString("error_msg"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                int error_code = finalObject1.getJSONObject("error").getInt("error_code");
                switch (error_code){
                    case(5):
                        new PrefWork(context).clearPref();
                        parent.startActivity(new Intent(context, AuthWeb.class));
                        parent.finish();
                        instance = null;
                        break;
                    case(10):
                        new PrefWork(context).clearPref();
                        parent.startActivity(new Intent(context, AuthWeb.class));
                        parent.finish();
                        instance = null;
                        break;
                }
            }
            if (!object.isNull("response")){
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
                return out;
            }
        }
        return out;
    }

    private boolean checkError(JSONObject o){
        if (o.optJSONObject("error").has("error_msg")) {
            return true;
        }
        return false;
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
