package com.retor.TestVKapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.retor.TestVKapp.help.Cons;
import com.retor.TestVKapp.help.PrefWork;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthWeb extends Activity {

    private WebView wv;
    static String TAG = "WebAuth";
    private PrefWork prefWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authweb);
        prefWork = new PrefWork(getApplicationContext());
        if (getSharedPreferences(Cons.PREF_NAME, MODE_MULTI_PROCESS).getString(Cons.TOKEN_PREF, null)!=null){
            Intent intent = new Intent(getApplicationContext(), NewsList.class);
            startActivity(intent);
            finish();
        }else {
            wv = (WebView) findViewById(R.id.webauth);
            wv.setWebViewClient(new AuthWebClient());
            wv.clearCache(true);
            wv.getSettings().setJavaScriptEnabled(true);
            String url = Cons.AUTH_URL;
            wv.loadUrl(url);
        }
    }

    class AuthWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            parseUrl(url);
        }
    }

    private void parseUrl(String url) {
        try {
            if(url==null)
                return;
            Log.i(TAG, "url=" + url);
            if(url.startsWith(Cons.REDIRECT_URL))
            {
                if(!url.contains("error=")){
                    String[] auth= parseRedirectUrl(url);
                    Intent intent=new Intent();
/*                    intent.putExtra("token", auth[0]);
                    intent.putExtra("user_id", Long.parseLong(auth[1]));*/
                    prefWork.savePref(Cons.PREF_NAME, auth[0], Long.parseLong(auth[1]));
                    Log.d("PrefSave", auth[0]+" "+auth[1]);
                    intent.setClass(this, NewsList.class);
                    startActivity(intent);
                    this.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String[] parseRedirectUrl(String url) throws Exception {
        //url is something like http://api.vkontakte.ru/blank.html#access_token=66e8f7a266af0dd477fcd3916366b17436e66af77ac352aeb270be99df7deeb&expires_in=0&user_id=7657164
        String access_token=extractPattern(url, "access_token=(.*?)&");
        Log.i(TAG, "access_token=" + access_token);
        String user_id=extractPattern(url, "user_id=(\\d*)");
        Log.i(TAG, "user_id=" + user_id);
        if(user_id==null || user_id.length()==0 || access_token==null || access_token.length()==0)
            throw new Exception("Failed to parse redirect url "+url);
        return new String[]{access_token, user_id};
    }

    public static String extractPattern(String string, String pattern){
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(string);
        if (!m.find())
            return null;
        return m.toMatchResult().group(1);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
