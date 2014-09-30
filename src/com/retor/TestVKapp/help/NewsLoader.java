package com.retor.TestVKapp.help;

import com.retor.TestVKapp.classes.Group;
import com.retor.TestVKapp.classes.News;
import com.retor.TestVKapp.classes.Profile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Антон on 29.09.2014.
 */
public class NewsLoader {

    public NewsLoader(){

    }

    public ArrayList<News> getNewsArray(JSONObject object) throws JSONException {
        ArrayList<News> out = new ArrayList<News>();
        JSONArray jsonArray = object.getJSONObject("response").getJSONArray("items");
        ArrayList<Profile> profiles = getProfArray(object.getJSONObject("response").getJSONArray("profiles"));
        ArrayList<Group> groups = getGroupArray(object.getJSONObject("response").getJSONArray("groups"));
        for (int i = 0; i < jsonArray.length(); i++){
            News news = new News();
            news = news.parse((JSONObject)jsonArray.get(i));
            if (news.source_id>0){
                news.setProfile(getProf(profiles, news.getSource_id()));
            }else{
                news.setGroup(getGroup(groups, news.getSource_id()));
            }
            if (news.copy_owner_id!=0){
                if (news.copy_owner_id>0){
                    news.setProfile(getProf(profiles, news.copy_owner_id));
                }else{
                    news.setGroup(getGroup(groups, news.copy_owner_id));
                }
            }else{
                if (news.signer_id!=0 && news.signer_id>0) {
                    news.setProfile(getProf(profiles, news.signer_id));
                }
            }
            //loadpics(news);
            out.add(news);
        }
        return out;
    }

    private Profile getProf(ArrayList<Profile> arrayin, long in){
        Profile profile = new Profile();
        for (int i=0; i< arrayin.size(); i++){
            if (arrayin.get(i).id == in)
                profile = arrayin.get(i);
        }
        //loadProfilePics(profile);
        return profile;
    }

    private Group getGroup(ArrayList<Group> arrayin, long in){
        Group group = new Group();
        for (int i=0; i< arrayin.size(); i++){
            if (((arrayin.get(i).id)*-1) == in || arrayin.get(i).id == in)
                group = arrayin.get(i);
        }
        //loadGroupPics(group);
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

/*    public void loadpics(News in){
        if (in.attachment!=null) {
            if (in.attachment.type.equals("album")) {
                try {
                    in.picture = Drawable.createFromStream((InputStream) new URL(in.attachment.album.thumb.photo_75).getContent(), "321");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in.attachment.type.equals("photo")) {
                try {
                    in.picture = Drawable.createFromStream((InputStream) new URL(in.attachment.photo.photo_75).getContent(), "321");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadProfilePics(Profile in){
        if (in!=null){
            try {
                in.picture = Drawable.createFromStream((InputStream) new URL(in.getPhoto_50()).getContent(), "profile");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadGroupPics(Group in){
        if (in!=null){
            try {
                in.picture = Drawable.createFromStream((InputStream) new URL(in.getPhoto_50()).getContent(), "group");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
}
