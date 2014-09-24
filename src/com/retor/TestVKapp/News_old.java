package com.retor.TestVKapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by retor on 11.09.2014.
 */
public class News_old {
/*    String postName;
    String postText;
    String author;
    int comments;
    int likes;
    Bitmap postPic;*/

    public String type;
    public long source_id;
    public long from_id;//когда пост приходит в комментариях, то в source_id там стена, а в from_id автор сообщения.
    public long date;
    public long post_id;

    //deprecated fields
    public long copy_owner_id;
    public long copy_post_id;
    public String copy_text;

    public String text;
    public long signer_id=0;

    //likes
    public int like_count;
    public boolean user_like;

    //comments
    public int comment_count;
    public boolean comment_can_post;

    //reposts
    public int reposts_count;
    public boolean user_reposted;

/*    public ArrayList<Attachment> attachments=new ArrayList<Attachment>();
    public Geo geo;
    public ArrayList<Photo> photos=new ArrayList<Photo>(); //except wall
    public ArrayList<Photo> photo_tags=new ArrayList<Photo>();//except wall
    public ArrayList<Note> notes;//except wall
    public ArrayList<String> friends;//uid //except wall*/

    public String comments_json;

    public News_old() {
    }

    public News_old parse(JSONObject o) {
        News_old newsitem = new News_old();
        try {
            newsitem.type = o.getString("post_type");
            //newsitem.source_id = Long.parseLong(o.getString("owner_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String from_id=o.optString("from_id");
        if(from_id!=null && !from_id.equals(""))
            newsitem.from_id = Long.parseLong(from_id);
        newsitem.date = o.optLong("date");
        newsitem.post_id = o.optLong("id");
        newsitem.text = o.optString("text");
        newsitem.copy_owner_id = o.optLong("copy_owner_id");
        newsitem.copy_text = o.optString("copy_text");
        //newsitem.signer_id = jitem.optLong("signer_id");//здесь нет этого поля
        //copy_post_date
        //copy_post_id
        //copy_post_type
/*        JSONArray attachments=o.optJSONArray("attachments");
        JSONObject geo_json=o.optJSONObject("geo");
        newsitem.attachments=Attachment.parseAttachments(attachments, newsitem.source_id, newsitem.copy_owner_id, geo_json);
        if (o.has(NewsJTags.COMMENTS)){
            JSONObject jcomments = o.getJSONObject(NewsJTags.COMMENTS);
            newsitem.comment_count = jcomments..optInt("count");//однажды была строка null
            newsitem.comment_can_post = jcomments.optInt("can_post")==1;
            JSONArray x=jcomments.optJSONArray("list");
            if(x!=null)
                newsitem.comments_json=x.toString();
        }
        if (o.has(NewsJTags.LIKES)){
            JSONObject jlikes = o.getJSONObject(NewsJTags.LIKES);
            newsitem.like_count = jlikes.optInt("count");
            newsitem.user_like = jlikes.optInt("user_likes")==1;
        }
        if (o.has(NewsJTags.REPOSTS)){
            JSONObject jlikes = o.getJSONObject(NewsJTags.REPOSTS);
            newsitem.reposts_count = jlikes.optInt("count");
            newsitem.user_reposted = jlikes.optInt("user_reposted")==1;
        }*/
        return newsitem;
    }
}
