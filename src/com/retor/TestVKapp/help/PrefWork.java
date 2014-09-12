package com.retor.TestVKapp.help;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by retor on 11.09.2014.
 */
public class PrefWork {
    static public Context context;
    public PrefWork(Context _context) {
        context=_context;
    }

    public void savePref(String prefName, String token, long userId){
        SharedPreferences preferences = context.getSharedPreferences(prefName, Context.MODE_MULTI_PROCESS);
        preferences.edit().putString(Cons.TOKEN_PREF, token).putLong(Cons.USERID_PREF, userId)
        .commit();
    }

    public String loadToken(){
        String out;
        SharedPreferences preferences = context.getSharedPreferences(Cons.PREF_NAME, Context.MODE_MULTI_PROCESS);
        out = preferences.getString(Cons.TOKEN_PREF, null);
        return out;
    }
    public long loadUserId(){
        long out;
        SharedPreferences preferences = context.getSharedPreferences(Cons.PREF_NAME, Context.MODE_MULTI_PROCESS);
        out = preferences.getLong(Cons.USERID_PREF, 0);
        return out;
    }
}
