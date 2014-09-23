package com.retor.TestVKapp.help;

import java.io.Serializable;
/**
 * Created by retor on 11.09.2014.
 */
public class Cons implements Serializable{
    static public String APP_ID = "4543903";
    static public String ACESS_TOKEN = "45D567AC58DE0919DD96EB4FDC90D23FE9C6AAB8";
    static public String SCOPE = "video,audio,wall,friends";
    static public String API_V = "5.24";
    static public String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    static public String AUTH_URL = "https://oauth.vk.com/authorize?"+"client_id="+APP_ID+"&"+"scope="+SCOPE+"&"+"redirect_uri="+REDIRECT_URL+"&"+"display=mobile&"+"v="+API_V+"&"+"response_type=token&revoke=0";
    static public String TOKEN_PREF = "token";
    static public String USERID_PREF = "userid";
    static public String PREF_NAME = "prefapp";
    //static public String NEWS_URL = "https://api.vk.com/method/newsfeed.get?user_id=" + id +  "&filters=post" +  "&count=2" + "&v=" + Cons.API_V + "&access_token=" + token;
}